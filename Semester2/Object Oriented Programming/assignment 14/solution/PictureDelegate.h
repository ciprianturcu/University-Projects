#pragma once
#include <QStyledItemDelegate>
#include <QPainter>

class PictureDelegate : public QStyledItemDelegate
{
public:
	PictureDelegate(QWidget* parent = 0);

	void paint(QPainter* painter, const QStyleOptionViewItem& option, const QModelIndex& index) const override;

	QSize sizeHint(const QStyleOptionViewItem& option, const QModelIndex& index) const override;
};