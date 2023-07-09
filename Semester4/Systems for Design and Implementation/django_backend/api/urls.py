from django.urls import path

from api.views.AttorneyOnLawsuitViews import AttorneyOnLawsuitList, AttorneyOnLawsuitDetail, AttorneyTravelView
from api.views.AttorneyViews import AttorneyList, AttorneyDetail
from api.views.ClientViews import ClientList, ClientDetail, LawsuitsOfClientList, ClientViewForAutocomplete
from api.views.LawsuitViews import LawsuitList, LawsuitDetail, ProfitStatisticView

urlpatterns = [
    path('attorney/', AttorneyList.as_view(), name="attorney"),
    path('attorney/<int:pk>/', AttorneyDetail.as_view()),
    path('lawsuit/', LawsuitList.as_view(), name="lawsuit"),
    path('lawsuit/<int:pk>/', LawsuitDetail.as_view()),
    path('client/', ClientList.as_view(), name="client"),
    path('client/<int:pk>/', ClientDetail.as_view()),
    path('aol/', AttorneyOnLawsuitList.as_view(), name="aol"),
    path('aol/<int:pk>/', AttorneyOnLawsuitDetail.as_view()),
    path('profits/', ProfitStatisticView.as_view()),
    path('travel_sheet/', AttorneyTravelView.as_view(), name="travel_sheet"),
    path('client/<int:id>/lawsuit/', LawsuitsOfClientList.as_view()),
    path('client/autocomplete/', ClientViewForAutocomplete.as_view())
]