#include "pictureDelegate.h"

PictureDelegate::PictureDelegate(QWidget* parent) : QStyledItemDelegate{ parent } {}

void PictureDelegate::paint(QPainter* painter, const QStyleOptionViewItem& option, const QModelIndex& index) const
{
	QString dog = index.model()->data(index, Qt::EditRole).toString();

	if (index.column() != 5)
	{
		QStyledItemDelegate::paint(painter, option, index);
		return;
	}

	if (dog.contains("Black"))
	{
		QPixmap pixMap("black.jpg");
		painter->drawPixmap(option.rect, pixMap);
	}
	else if (dog.contains("Green"))
	{
		QPixmap pixMap("green.jpg");
		painter->drawPixmap(option.rect, pixMap);
	}
	else if (dog.contains("Red"))
	{
		QPixmap pixMap("red.jpg");
		painter->drawPixmap(option.rect, pixMap);
	}
	else if (dog.contains("White"))
	{
		QPixmap pixMap("white.jpg");
		painter->drawPixmap(option.rect, pixMap);
	}
	else
	{
		QPixmap pixMap("noimage.jpg");
		painter->drawPixmap(option.rect, pixMap);
	}
}

QSize PictureDelegate::sizeHint(const QStyleOptionViewItem& option, const QModelIndex& index) const
{
	if (index.column() == 1)
	{
		return QSize(32, 64);
	}
	return QStyledItemDelegate::sizeHint(option, index);
}


