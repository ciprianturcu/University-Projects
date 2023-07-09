from django.db.models import F
from rest_framework import generics

from api.models.AttorneyOnLawsuit import AttorneyOnLawsuit
from api.serializers.AttorneyOnLawsuitSerializers import AttorneyOnLawsuitSerializer, AttorneyTravelDTO
from api.views.Pagination import CustomPagination


class AttorneyOnLawsuitList(generics.ListCreateAPIView):
    serializer_class = AttorneyOnLawsuitSerializer
    pagination_class = CustomPagination
    queryset = AttorneyOnLawsuit.objects.all()

    # def list(self, request, *args, **kwargs):
    #     queryset = self.get_queryset()
    #     id_list = list(queryset.values_list('id', flat=True))
    #     return Response(id_list)


class AttorneyOnLawsuitDetail(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = AttorneyOnLawsuitSerializer
    queryset = AttorneyOnLawsuit.objects.all()


class AttorneyTravelView(generics.ListAPIView):
    serializer_class = AttorneyTravelDTO
    pagination_class = CustomPagination

    def get_queryset(self):
        queryset = AttorneyOnLawsuit.objects.exclude(attorney__city=F('lawsuit__state')) \
            .annotate(name=F('attorney__name')).annotate(city=F('attorney__city')) \
            .annotate(lawsuit_state=F('lawsuit__state'))
        return queryset
