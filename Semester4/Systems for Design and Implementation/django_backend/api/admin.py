from django.contrib import admin

from api.models.Attorney import Attorney
from api.models.AttorneyOnLawsuit import AttorneyOnLawsuit
from api.models.Client import Client
from api.models.Lawsuit import Lawsuit

admin.site.register(Attorney)
admin.site.register(Lawsuit)
admin.site.register(Client)
admin.site.register(AttorneyOnLawsuit)
