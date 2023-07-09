import re

from rest_framework import serializers

from api.models.Client import Client
from api.serializers.LawsuitSerializers import LawsuitSerializer


class ClientSerializer(serializers.ModelSerializer):
    lawsuits = LawsuitSerializer(many=True, read_only=True)
    nb_lawsuits = serializers.IntegerField(read_only=True)
    def validate(self, data):
        pattern_city = r'^[a-zA-Z\s-]+$'
        pattern_name = r'^[a-zA-Z\s]+$'
        if not bool(re.match(pattern_name, data['name'])):
            raise serializers.ValidationError('Name must contain only letters and spaces')
        if not data['phone_number'].isdigit():
            raise serializers.ValidationError('Phone number must contain only digits!')
        if len(data['phone_number']) != 10:
            raise serializers.ValidationError('Phone number must be 10 digits long!')
        if not bool(re.match(pattern_city, data['city'])):
            raise serializers.ValidationError('City must contain only letters and  spaces')
        return data

    class Meta:
        model = Client
        fields = ('id','name', 'phone_number', 'city', 'date_of_birth', 'type', 'nb_lawsuits','lawsuits')

class ClientForAutocompleteSerializer(serializers.ModelSerializer):
    class Meta:
        model = Client
        fields = '__all__'
