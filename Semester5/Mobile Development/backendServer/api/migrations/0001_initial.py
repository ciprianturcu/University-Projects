# Generated by Django 5.0 on 2023-12-26 12:36

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Game',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.CharField(max_length=50)),
                ('description', models.CharField(max_length=250)),
                ('genre', models.CharField(max_length=50)),
                ('progress', models.FloatField()),
                ('rating', models.FloatField()),
                ('hoursPlayed', models.IntegerField()),
            ],
            options={
                'ordering': ['id'],
            },
        ),
        migrations.AddConstraint(
            model_name='game',
            constraint=models.CheckConstraint(check=models.Q(('progress__gte', 0)), name='progress_lower_bound'),
        ),
        migrations.AddConstraint(
            model_name='game',
            constraint=models.CheckConstraint(check=models.Q(('progress__lte', 100)), name='progress_upper_bound'),
        ),
        migrations.AddConstraint(
            model_name='game',
            constraint=models.CheckConstraint(check=models.Q(('rating__gte', 0)), name='rating_lower_bound'),
        ),
        migrations.AddConstraint(
            model_name='game',
            constraint=models.CheckConstraint(check=models.Q(('rating__lte', 5)), name='rating_upper_bound'),
        ),
        migrations.AddConstraint(
            model_name='game',
            constraint=models.CheckConstraint(check=models.Q(('hoursPlayed__gte', 0)), name='hoursPlayed_lower_bound'),
        ),
    ]
