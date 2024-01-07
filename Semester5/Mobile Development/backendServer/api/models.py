from django.db import models


class Game(models.Model):
    title = models.CharField(max_length=50, null=False)
    description = models.CharField(max_length=250, null=False)
    genre = models.CharField(max_length=50, null=False)
    progress = models.FloatField(null=False)
    rating = models.FloatField(null=False)
    hoursPlayed = models.IntegerField(null=False)

    def __str__(self):
        return self.title

    class Meta:
        ordering = ['id']
        constraints = [
            models.CheckConstraint(check=models.Q(progress__gte=0), name='progress_lower_bound'),
            models.CheckConstraint(check=models.Q(progress__lte=100), name='progress_upper_bound'),
            models.CheckConstraint(check=models.Q(rating__gte=0), name='rating_lower_bound'),
            models.CheckConstraint(check=models.Q(rating__lte=5), name='rating_upper_bound'),
            models.CheckConstraint(check=models.Q(hoursPlayed__gte=0), name='hoursPlayed_lower_bound'),
        ]
