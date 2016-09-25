package za.co.ibear.data.unit.swt.product;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import za.co.ibear.data.unit.product.ProductModel;

public class ProductLabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		ProductModel product = (ProductModel) element;
		switch (columnIndex) {
		case 0:
			return String.valueOf(product.getProduct());
		case 1:
			return String.valueOf(product.getDescription());
		case 2:
			return String.valueOf(product.getProductCategory());
		case 3:
			return String.valueOf(product.getTimeCreatedToString());
		case 4:
			return String.valueOf(product.getUser());
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