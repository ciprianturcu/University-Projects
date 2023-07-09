from rest_framework import serializers

from api.models.AttorneyOnLawsuit import AttorneyOnLawsuit


class AttorneyOnLawsuitSerializer(serializers.ModelSerializer):
    class Meta:
        model = AttorneyOnLawsuit
        depth = 1
        fields = '__all__'


class AttorneyTravelDTO(serializers.Serializer):
    name = serializers.CharField()
    city = serializers.CharField()
    lawsuit_state = serializers.CharField()

    class Meta:
        ordering = ['name']