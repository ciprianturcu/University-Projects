#include "UndoRedo.h"

UndoRedoAdd::UndoRedoAdd(Coat c, Repository& repo) : addedCoat{ c }, repo{ repo } {}


void UndoRedoAdd::undo()
{
	this->repo.deleteCoat(this->addedCoat);
}

void UndoRedoAdd::redo()
{
	this->repo.addCoat(this->addedCoat);
}

UndoRedoDelete::UndoRedoDelete(Coat c, Repository& repo) : deletedCoat{ c }, repo{ repo }{}

void UndoRedoDelete::undo()
{
	this->repo.addCoat(this->deletedCoat);

}

void UndoRedoDelete::redo()
{
	this->repo.deleteCoat(this->deletedCoat);
}

UndoRedoUpdate::UndoRedoUpdate(Coat oC, Coat c, Repository& r) : oldCoat{ oC }, updatedCoat{ c }, repo{ r }{}


void UndoRedoUpdate::undo()
{
	this->repo.updateCoat(this->updatedCoat, this->oldCoat);
}

void UndoRedoUpdate::redo()
{
	this->repo.updateCoat(this->oldCoat, this->updatedCoat);
}

UndoRedoBasket::UndoRedoBasket(Coat c, Basket* b, Repository& r) : transferedCoat{ c }, basket{ b }, repo{r}{}

void UndoRedoBasket::undo()
{
	int pos = this->repo.getPosOfCoat(this->transferedCoat);
	int nr = this->repo.getCoatFromPos(pos).getQuantity() + 1;
	Coat c = this->repo.getCoatFromPos(pos);
	Coat u = c;
	u.setQuantity(nr);
	this->repo.updateCoat(c, u);
	this->basket->remove(this->transferedCoat);
}

void UndoRedoBasket::redo()
{
	int pos = this->repo.getPosOfCoat(this->transferedCoat);
	int nr = this->repo.getCoatFromPos(pos).getQuantity() - 1;
	Coat c = this->repo.getCoatFromPos(pos);
	Coat u = c;
	u.setQuantity(nr);
	this->repo.updateCoat(c, u);
	this->basket->add(this->transferedCoat);
}
