from django.db import models


class Attorney(models.Model):
    types = ['Criminal', 'Civil', 'Family', 'Commercial', 'Juvenile', 'Tax']
    Choice = sorted([(item, item) for item in types])
    name = models.CharField(max_length=100)
    specialization = models.CharField(max_length=50,
                                      choices=Choice)
    date_of_birth = models.DateField()
    experience = models.CharField(max_length=50, choices=[('Junior', 'Junior'), ('Mid', 'Mid'), ('Senior', 'Senior')])
    city = models.CharField(max_length=100)
    fee = models.IntegerField()

    def __str__(self):
        return self.name

    class Meta:
        ordering = ['id']
        indexes=[models.Index(fields=["name"]),
                 models.Index(fields=["city", "id"])]