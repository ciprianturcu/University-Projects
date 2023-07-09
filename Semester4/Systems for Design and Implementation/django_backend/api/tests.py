from django.test import TestCase

from api.models import Client, Lawsuit, Attorney, AttorneyOnLawsuit


class LawsuitProfitsTestcase(TestCase):
    @classmethod
    def setUpTestData(cls):
        client_1 = Client.objects.create(name="C1", phoneNumber=121, city='Cluj-Napoca', date_of_birth='2000-01-01', type='Physical Person')
        client_2 = Client.objects.create(name="C2", phoneNumber=122, city='Cluj-Napoca', date_of_birth='2000-01-01',
                                         type='Juridical Person')
        client_3 = Client.objects.create(name="C3", phoneNumber=123, city='Cluj-Napoca', date_of_birth='2000-01-01', type='Physical Person')
        client_4 = Client.objects.create(name="C4", phoneNumber=124, city='Bucuresti', date_of_birth='2000-01-01', type='Juridical Person')

        lawsuit_1 = Lawsuit.objects.create(description='D1', type='Criminal', state='Cluj-Napoca',
                                           courtDate='2024-01-01', client=client_1)
        lawsuit_2 = Lawsuit.objects.create(description='D2', type='Civil', state='Cluj-Napoca', courtDate='2024-01-01',
                                           client=client_2)
        lawsuit_3 = Lawsuit.objects.create(description='D3', type='Criminal', state='Cluj-Napoca',
                                           courtDate='2024-01-01', client=client_3)
        lawsuit_4 = Lawsuit.objects.create(description='D4', type='Civil', state='Bucuresti', courtDate='2024-01-01',
                                           client=client_4)

        attorney_1 = Attorney.objects.create(name='A1', specialization='Criminal', date_of_birth='2000-01-01', experience='Junior',
                                             city='Cluj-Napoca', fee=4000)
        attorney_2 = Attorney.objects.create(name='A2', specialization='Criminal', date_of_birth='2000-01-01', experience='Junior',
                                             city='Bucuresti', fee=3000)
        attorney_3 = Attorney.objects.create(name='A3', specialization='Civil', date_of_birth='2000-01-01', experience='Junior',
                                             city='Targu Mures', fee=2000)
        attorney_4 = Attorney.objects.create(name='A4', specialization='Family', date_of_birth='2000-01-01', experience='Mid',
                                             city='Cluj-Napoca', fee=10000)
        attorney_5 = Attorney.objects.create(name='A5', specialization='Family', date_of_birth='2000-01-01', experience='Mid',
                                             city='Bucuresti', fee=10000)
        attorney_6 = Attorney.objects.create(name='A6', specialization='Civil', date_of_birth='2000-01-01', experience='Mid',
                                             city='Targu Mures', fee=8000)
        attorney_7 = Attorney.objects.create(name='A7', specialization='Commercial', date_of_birth='2000-01-01', experience='Senior',
                                             city='Cluj-Napoca', fee=50000)
        attorney_8 = Attorney.objects.create(name='A8', specialization='Juvenile', date_of_birth='2000-01-01', experience='Senior',
                                             city='Bucuresti', fee=43000)
        attorney_9 = Attorney.objects.create(name='A9', specialization='Tax', date_of_birth='2000-01-01', experience='Senior',
                                             city='Targu Mures', fee=26000)

        aol_1 = AttorneyOnLawsuit.objects.create(attorney=attorney_1, lawsuit=lawsuit_1, attRole='Secondary',
                                                 workType='Documents')
        aol_2 = AttorneyOnLawsuit.objects.create(attorney=attorney_4, lawsuit=lawsuit_1, attRole='Secondary',
                                                 workType='Evidence collection')
        aol_3 = AttorneyOnLawsuit.objects.create(attorney=attorney_7, lawsuit=lawsuit_1, attRole='Primary',
                                                 workType='Statement preparation')
        aol_4 = AttorneyOnLawsuit.objects.create(attorney=attorney_2, lawsuit=lawsuit_2, attRole='Secondary',
                                                 workType='Documents')
        aol_5 = AttorneyOnLawsuit.objects.create(attorney=attorney_5, lawsuit=lawsuit_2, attRole='Secondary',
                                                 workType='Evidence collection')
        aol_6 = AttorneyOnLawsuit.objects.create(attorney=attorney_8, lawsuit=lawsuit_2, attRole='Primary',
                                                 workType='Statement preparation')
        aol_7 = AttorneyOnLawsuit.objects.create(attorney=attorney_3, lawsuit=lawsuit_3, attRole='Secondary',
                                                 workType='Documents')
        aol_8 = AttorneyOnLawsuit.objects.create(attorney=attorney_6, lawsuit=lawsuit_3, attRole='Primary',
                                                 workType='Documents')
        aol_9 = AttorneyOnLawsuit.objects.create(attorney=attorney_9, lawsuit=lawsuit_3, attRole='Secondary',
                                                 workType='Statement preparation')
        aol_10 = AttorneyOnLawsuit.objects.create(attorney=attorney_4, lawsuit=lawsuit_4, attRole='Secondary',
                                                  workType='Evidence collection')
        aol_11 = AttorneyOnLawsuit.objects.create(attorney=attorney_5, lawsuit=lawsuit_4, attRole='Secondary',
                                                  workType='Documents')
        aol_12 = AttorneyOnLawsuit.objects.create(attorney=attorney_6, lawsuit=lawsuit_4, attRole='Primary',
                                                  workType='Evidence collection')

    def test_top_3_profits(self):
        response = self.client.get("/api/profits/")

        self.assertEqual(len(response.data), 3)
        self.assertEqual((response.data[0]['profit']), 64000)


class AttorneyFeeTestCase(TestCase):
    @classmethod
    def setUpTestData(cls):
        attorney_1 = Attorney.objects.create(name='A1', specialization='Criminal', date_of_birth='2000-01-01', experience='Junior',
                                             city='Cluj-Napoca', fee=5100)
        attorney_2 = Attorney.objects.create(name='A2', specialization='Criminal', date_of_birth='2000-01-01', experience='Junior',
                                             city='Bucuresti', fee=3000)
        attorney_3 = Attorney.objects.create(name='A3', specialization='Civil', date_of_birth='2000-01-01', experience='Junior',
                                             city='Targu Mures', fee=2000)
        attorney_4 = Attorney.objects.create(name='A4', specialization='Family', date_of_birth='2000-01-01', experience='Mid',
                                             city='Cluj-Napoca', fee=10000)

    def test_attorney_fee_grater_than_5000(self):
        response = self.client.get("/api/attorney/?fee__gt=5000")

        self.assertEqual(len(response.data), 2)
