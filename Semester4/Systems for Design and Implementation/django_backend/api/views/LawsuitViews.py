from django.db.models import Sum, F, Count
from rest_framework import generics

from api.models.Lawsuit import Lawsuit
from api.serializers.LawsuitSerializers import LawsuitSerializer, LawsuitProfitReportDTO, LawsuitSerializerWithDepth
from api.views.Pagination import CustomPagination


class LawsuitList(generics.ListCreateAPIView):
    serializer_class = LawsuitSerializer
    pagination_class = CustomPagination

    def get_queryset(self):
        queryset = Lawsuit.objects.all().annotate(nb_attorneys=Count("lawsuits"))
        client = self.request.query_params.get('client')
        if client is not None:
            queryset = queryset.filter(client=client)
        return queryset

    # def list(self, request, *args, **kwargs):
    #     queryset = self.get_queryset()
    #     id_list = list(queryset.values_list('id', flat=True))
    #     return Response(id_list)


class LawsuitDetail(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = LawsuitSerializerWithDepth
    queryset = Lawsuit.objects.all()


class ProfitStatisticView(generics.ListAPIView):
    serializer_class = LawsuitProfitReportDTO

    def get_queryset(self):
        queryset = Lawsuit.objects.annotate(profit=Sum('attorneyonlawsuit__attorney__fee')).order_by(
            F('profit').desc(nulls_last=True))[:3]
        return queryset
