import json

from asgiref.sync import async_to_sync
from channels.layers import get_channel_layer
from rest_framework import generics, status
from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.views import APIView

from api.models import Game
from api.serializers.gameSerializer import GameSerializer


def get_client_ip(request):
    x_forwarded_for = request.META.get('HTTP_X_FORWARDED_FOR')
    if x_forwarded_for:
        ip = x_forwarded_for.split(',')[0]
    else:
        ip = request.META.get('REMOTE_ADDR')
    return ip


class GameListView(generics.ListCreateAPIView):
    serializer_class = GameSerializer
    queryset = Game.objects.all()

    def perform_create(self, serializer):
        serializer.save()
        channel_layer = get_channel_layer()
        async_to_sync(channel_layer.group_send)(
            "games_group",
            {
                "type": "game_update",
                "sender_ip": get_client_ip(self.request),
                "message": {
                    "action": "create",
                    "game": serializer.data
                }
            }
        )


class GameRetrieveUpdateDestroyView(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = GameSerializer
    queryset = Game.objects.all()

    def perform_update(self, serializer):
        serializer.save()

        channel_layer = get_channel_layer()
        async_to_sync(channel_layer.group_send)(
            "games_group",
            {
                "type": "game_update",
                "sender_ip": get_client_ip(self.request),
                "message": {
                    "action": "update",
                    "game": serializer.data
                }
            }
        )

    def perform_destroy(self, instance):
        channel_layer = get_channel_layer()
        serialized_data = GameSerializer(instance).data
        instance.delete()
        async_to_sync(channel_layer.group_send)(
            "games_group",
            {
                "type": "game_update",
                "sender_ip": get_client_ip(self.request),
                "message": {
                    "action": "delete",
                    "game": serialized_data
                }
            }
        )

class BulkGameCreateView(APIView):

    def post(self, request, *args, **kwargs):
        # Check if data is provided in the request body
        if not request.data:
            return Response({"detail": "No data provided"}, status=status.HTTP_400_BAD_REQUEST)

        # Extract list of game data directly from request body
        games_data = request.data

        # Create a list to store the created game objects
        created_games = []

        # Process each game data and save to the database
        for game_data in games_data:
            serializer = GameSerializer(data=game_data)
            if serializer.is_valid():
                serializer.save()
                created_games.append(serializer.data)
            else:
                # If any data is invalid, return error response
                return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

        # Send a group message using channels
        channel_layer = get_channel_layer()
        async_to_sync(channel_layer.group_send)(
            "games_group",
            {
                "type": "game_update",
                "sender_ip": get_client_ip(request),
                "message": {
                    "action": "bulk_create",
                    "game": json.dumps(created_games)
                }
            }
        )

        # Return success response with created games data
        return Response(created_games, status=status.HTTP_201_CREATED)
