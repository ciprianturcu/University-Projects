from django.db import models

from api.models.Client import Client


class Lawsuit(models.Model):
    types = ['Criminal', 'Civil', 'Family', 'Commercial', 'Juvenile', 'Tax']
    Choice = sorted([(item, item) for item in types])

    description = models.CharField(max_length=100)
    type = models.CharField(max_length=50,
                            choices=Choice)
    state = models.CharField(max_length=50)
    court_date = models.DateField()
    client = models.ForeignKey(Client, on_delete=models.CASCADE, related_name='lawsuits')

    def __str__(self):
        return self.description

    class Meta:
        ordering = ['id']
        indexes = [models.Index(fields=["client", "id"]),
                   models.Index(fields=["state", "id"]),
                   models.Index(fields=["description"])]