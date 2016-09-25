package za.co.ibear.data.unit.swt.productcategory;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import za.co.ibear.data.unit.productcategory.ProductCategoryModel;

public class ProductCategoryLabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		ProductCategoryModel productcategory = (ProductCategoryModel) element;
		switch (columnIndex) {
		case 0:
			return String.valueOf(productcategory.getProductCategory());
		case 1:
			return String.valueOf(productcategory.getDescription());
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