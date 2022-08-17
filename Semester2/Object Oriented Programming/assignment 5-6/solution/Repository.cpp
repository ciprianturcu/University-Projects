#include "Repository.h"
#include <string>
#include <assert.h>

Repository::Repository()
{
}

Repository::~Repository()
{
}

Coat* Repository::getAllCoats() const
{
	return arr.get_all_elems();
}

bool Repository::addCoat(const Coat& new_coat)
{
	int position = arr.get_size();
	arr.add_element(new_coat);
	if (arr.get_size() - 1 == position)
		return true;
	else
		return false;
}

bool Repository::deleteCoat(const Coat& coat)
{
	int size = arr.get_size();
	arr.delete_element(coat);
	if (arr.get_size() == size - 1)
		return true;
	else
		return false;
}

bool Repository::updateCoat(const Coat& i_coat, const Coat& n_coat)
{
	int pos = arr.position_element(i_coat);
	if (pos == -1)
		return false;
	arr.update_element(i_coat, n_coat);
	return true;
}

int Repository::getArrSize() const
{
	return arr.get_size();
}

Coat Repository::getCoatFromPos(int pos) const
{
	return arr.get_element_from_position(pos);
}

Coat Repository::getCoatByAtr(std::string size, std::string colour, double price, std::string photo_link)
{
	for (int i = 0; i < arr.get_size(); i++)
	{
		Coat c = arr.get_element_from_position(i);
		if (c.getSize() == size && c.getColour() == colour && c.getPrice() == price && c.getLink() == photo_link)
			return c;
	}
	return Coat();
}


