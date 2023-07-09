from django.db.models import Count
from rest_framework import status, generics
from rest_framework.views import APIView

from rest_framework.response import Response

from api.models.Client import Client
from api.serializers.ClientSerializers import ClientSerializer, ClientForAutocompleteSerializer
from api.serializers.LawsuitSerializers import LawsuitSerializer
from api.views.Pagination import CustomPagination


class LawsuitsOfClientList(APIView):
    def post(self, request, id):
        lawsuits = request.data
        msg = "CREATED"

        for lawsuit_data in lawsuits:
            lawsuit_data['client'] = id
            serializer = LawsuitSerializer(data=lawsuit_data)
            if serializer.is_valid():
                serializer.save()
        return Response(msg, status=status.HTTP_201_CREATED)


class ClientList(generics.ListCreateAPIView):
    serializer_class = ClientSerializer
    pagination_class = CustomPagination
    queryset = Client.objects.all().annotate(nb_lawsuits=Count("lawsuits"))

    # def list(self, request, *args, **kwargs):
    #     queryset = self.get_queryset()
    #     id_list = list(queryset.values_list('id', flat=True))
    #     return Response(id_list)


class ClientDetail(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = ClientSerializer
    queryset = Client.objects.all()


class ClientViewForAutocomplete(APIView):
    serializer_class = ClientForAutocompleteSerializer

    def get(self, request, *args, **kwargs):
        query = request.GET.get('query')
        clients = Client.objects.filter(name__icontains=query).order_by('name')[:20]
        serializer = ClientForAutocompleteSerializer(clients, many=True)
        return Response(serializer.data)
