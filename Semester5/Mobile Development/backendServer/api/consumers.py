from channels.generic.websocket import AsyncWebsocketConsumer
import json


class GameConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        print("connect")
        await self.channel_layer.group_add("games_group", self.channel_name)
        await self.accept()

    async def disconnect(self, close_code):
        await self.channel_layer.group_discard("games_group", self.channel_name)

    async def game_update(self, event):
        # Send the message to WebSocket
        print("game_update")
        if event.get("sender_ip") != self.scope["client"][0]:
            print("client")
            await self.send(text_data=json.dumps(event["message"]))
