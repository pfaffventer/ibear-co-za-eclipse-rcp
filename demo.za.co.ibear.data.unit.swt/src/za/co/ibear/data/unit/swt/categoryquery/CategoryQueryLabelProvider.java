package za.co.ibear.data.unit.swt.categoryquery;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import za.co.ibear.data.unit.categoryquery.CategoryQueryModel;
public class CategoryQueryLabelProvider implements ITableLabelProvider {
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}
	public String getColumnText(Object element, int columnIndex) {
		CategoryQueryModel categoryquery = (CategoryQueryModel) element;
		switch (columnIndex) {
		case 0:
			return String.valueOf(categoryquery.getProductCategory());
		case 1:
			return String.valueOf(categoryquery.getDescription());
		case 2:
			return String.valueOf(categoryquery.getName());
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
