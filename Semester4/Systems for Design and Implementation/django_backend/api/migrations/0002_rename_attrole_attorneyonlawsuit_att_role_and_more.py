# Generated by Django 4.1.7 on 2023-04-22 19:03

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0001_initial'),
    ]

    operations = [
        migrations.RenameField(
            model_name='attorneyonlawsuit',
            old_name='attRole',
            new_name='att_role',
        ),
        migrations.RenameField(
            model_name='attorneyonlawsuit',
            old_name='workType',
            new_name='work_type',
        ),
        migrations.RenameField(
            model_name='client',
            old_name='phoneNumber',
            new_name='phone_number',
        ),
        migrations.RenameField(
            model_name='lawsuit',
            old_name='courtDate',
            new_name='court_date',
        ),
        migrations.AddField(
            model_name='attorneyonlawsuit',
            name='description',
            field=models.CharField(default='', max_length=500),
        ),
    ]