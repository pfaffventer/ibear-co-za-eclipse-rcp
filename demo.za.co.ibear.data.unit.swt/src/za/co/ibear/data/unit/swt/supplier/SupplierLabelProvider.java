package za.co.ibear.data.unit.swt.supplier;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import za.co.ibear.data.unit.supplier.SupplierModel;

public class SupplierLabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		SupplierModel supplier = (SupplierModel) element;
		switch (columnIndex) {
		case 0:
			return String.valueOf(supplier.getSupplier());
		case 1:
			return String.valueOf(supplier.getName());
		case 2:
			return String.valueOf(supplier.getAddress1());
		case 3:
			return String.valueOf(supplier.getAddress2());
		case 4:
			return String.valueOf(supplier.getAddress3());
		case 5:
			return String.valueOf(supplier.getAddress4());
		case 6:
			return String.valueOf(supplier.getAddress5());
		case 7:
			return String.valueOf(supplier.getUser());
		case 8:
			return String.valueOf(supplier.getTimeCreatedToString());
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