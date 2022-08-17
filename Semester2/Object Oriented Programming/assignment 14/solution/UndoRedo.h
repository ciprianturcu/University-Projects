#pragma once
#include "Repository.h"
#include "Basket.h"

class UndoRedo {
public:
	virtual void undo() = 0;
	virtual void redo() = 0;
};

class UndoRedoAdd : public UndoRedo {
private:
	Coat addedCoat;
	Repository& repo;
public:
	UndoRedoAdd(Coat c, Repository& repo);
	void undo() override;
	void redo() override;

};

class UndoRedoDelete : public UndoRedo
{
private:
	Coat deletedCoat;
	Repository& repo;
public:
	UndoRedoDelete(Coat c, Repository& repo);
	void undo() override;
	void redo() override;

};

class UndoRedoUpdate : public UndoRedo {
private:
	Coat oldCoat;
	Coat updatedCoat;
	Repository& repo;

public:
	UndoRedoUpdate(Coat oC, Coat c, Repository& r);
	void undo() override;
	void redo() override;
};

class UndoRedoBasket : public UndoRedo {
private:
	Coat transferedCoat;
	Basket* basket;
	Repository& repo;

public:
	UndoRedoBasket(Coat c, Basket* b, Repository& r);
	void undo() override;
	void redo() override;
};