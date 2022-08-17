#include "Ui.h"

int main()
{
	Repository repo = Repository();
	Ui ui = Ui(repo);
	ui.start_app();
	return 0;
}