from django.db import models


class Client(models.Model):
    name = models.CharField(max_length=100)
    phone_number = models.CharField(max_length=10)
    city = models.CharField(max_length=50)
    date_of_birth = models.DateField()
    type = models.CharField(max_length=50,
                            choices=[('Physical Person', 'Physical Person'), ('Juridical Person', 'Juridical Person')])

    def __str__(self):
        return self.name

    class Meta:
        ordering = ['id']
        indexes=[models.Index(fields=["name"])]