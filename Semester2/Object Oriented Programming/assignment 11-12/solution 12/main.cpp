#include <memory>
#include "Service.h"
#include "CVSBasket.h"
#include "Gui.h"

int main(int argc, char** argv)
{
	QApplication a(argc, argv);
	
	const Repository& r = Repository();
	Service service = Service();
	Gui gui{service};
	gui.show();
	return a.exec();
}
