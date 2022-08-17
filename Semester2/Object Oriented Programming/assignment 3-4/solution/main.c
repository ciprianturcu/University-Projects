#include "CountryRepository.h"
#include "Operation.h"
#include "Ui.h"
#include <crtdbg.h>
#include <stdio.h>

int main()
{

	testVector();
	testOperation();
	testRepo();
	testService();
	CountryRepo* repo = createRepo();

	Service* service = createService(repo);
	
	Ui* ui = createUI(service);
	initializeData(service);
	startUi(ui);
	destroyUi(ui);
	

	printf("%d",_CrtDumpMemoryLeaks());
	return 0;
}