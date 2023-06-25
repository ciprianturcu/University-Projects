--exec addRowAttorney 'Mircea', 'Bravo', 1000, 'Senior', 42, 'Criminal', 'Cluj-Napoca'
--exec addRowAttorney 'Andrei', 'Dumitru', 2000, 'Senior', 42, 'Criminal', 'Cluj-Napoca'
--exec addRowAttorney 'Cristi', 'Nol', 3000, 'Senior', 42, 'Criminal', 'Cluj-Napoca'

--table A: Attorney, table B: Invoice

begin tran
update Attorney set specialization='Civil' where id = 1
waitfor delay '00:00:10'
update Invoice set services_description='servdead1' where id = 2
commit tran