package za.co.ibear.data.unit.swt.purchaseorderline;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import za.co.ibear.data.unit.purchaseorderline.PurchaseOrderLineModel;

public class PurchaseOrderLineLabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		PurchaseOrderLineModel purchaseorderline = (PurchaseOrderLineModel) element;
		switch (columnIndex) {
		case 0:
			return String.valueOf(purchaseorderline.getProduct());
		case 1:
			return String.valueOf(purchaseorderline.getDescription());
		case 2:
			return String.valueOf(purchaseorderline.getVolume());
		case 3:
			return String.valueOf(purchaseorderline.getPrice());
		case 4:
			return String.valueOf(purchaseorderline.getTotal());
		}
		return null;
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

}