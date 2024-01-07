from django.urls import path
from api.views import GameListView, GameRetrieveUpdateDestroyView, BulkGameCreateView

urlpatterns = [
    path('games/', GameListView.as_view()),
    path('game/<int:pk>/', GameRetrieveUpdateDestroyView.as_view()),
    path('games/bulk_create/', BulkGameCreateView.as_view(), name='bulk_game_create'),
]
