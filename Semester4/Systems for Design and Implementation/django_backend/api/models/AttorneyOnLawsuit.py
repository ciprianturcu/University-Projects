from django.db import models

from api.models.Attorney import Attorney
from api.models.Lawsuit import Lawsuit


class AttorneyOnLawsuit(models.Model):
    attorney = models.ForeignKey(Attorney, on_delete=models.CASCADE, related_name="attorneys")
    lawsuit = models.ForeignKey(Lawsuit, on_delete=models.CASCADE, related_name="lawsuits")
    att_role = models.CharField(max_length=50, choices=[('Primary', 'Primary'), ('Secondary', 'Secondary')])
    work_type = models.CharField(max_length=50,
                                choices=[('Documents', 'Documents'), ('Statement preparation', 'Statement preparation'),
                                         ('Evidence collection', 'Evidence collection')])
    description = models.CharField(max_length=500, default="")

    class Meta:
        ordering = ['id']
        indexes = [models.Index(fields=["attorney", "lawsuit"]),
                   models.Index(fields=["attorney"]),
                   models.Index(fields=["lawsuit"]),
                   models.Index(fields=["att_role"]),
                   models.Index(fields=["work_type"]),
                   models.Index(fields=["description"]),
                   ]