package za.co.ibear.data.unit.swt.purchaseorder;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import za.co.ibear.data.unit.purchaseorder.PurchaseOrderModel;

public class PurchaseOrderLabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		PurchaseOrderModel purchaseorder = (PurchaseOrderModel) element;
		switch (columnIndex) {
		case 0:
			return String.valueOf(purchaseorder.getPurchaseOrder());
		case 1:
			return String.valueOf(purchaseorder.getSupplier());
		case 2:
			return String.valueOf(purchaseorder.getName());
		case 3:
			return String.valueOf(purchaseorder.getUser());
		case 4:
			return String.valueOf(purchaseorder.getTimeCreatedToString());
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