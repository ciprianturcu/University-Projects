import re

from rest_framework import serializers

from api.models.Lawsuit import Lawsuit


class LawsuitSerializer(serializers.ModelSerializer):
    nb_attorneys = serializers.IntegerField(read_only=True)
    def validate(self, data):
        pattern = r'^[a-zA-Z\s-]+$'
        if not bool(re.match(pattern, data['state'])):
            raise serializers.ValidationError('City must contain only letters, spaces and hyphens')
        return data

    class Meta:
        model = Lawsuit
        fields = '__all__'

class LawsuitSerializerWithDepth(serializers.ModelSerializer):
    def validate(self, data):
        pattern = r'^[a-zA-Z\s-]+$'
        if not bool(re.match(pattern, data['state'])):
            raise serializers.ValidationError('City must contain only letters, spaces and hyphens')
        return data
    class Meta:
        model = Lawsuit
        depth = 1
        fields = '__all__'



class LawsuitProfitReportDTO(serializers.Serializer):
    description = serializers.CharField()
    profit = serializers.IntegerField(default=0)
