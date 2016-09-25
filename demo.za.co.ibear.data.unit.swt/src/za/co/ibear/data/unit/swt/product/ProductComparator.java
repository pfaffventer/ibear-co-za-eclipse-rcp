package za.co.ibear.data.unit.swt.product;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import za.co.ibear.data.unit.product.ProductModel;
public class ProductComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;
	public ProductComparator() {
		this.propertyIndex = 0;
		direction = DESCENDING;
	}
	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}
	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			direction = 1 - direction;
		} else {
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		ProductModel model1 = (ProductModel) e1;
		ProductModel model2 = (ProductModel) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = model1.getProduct().compareTo(model2.getProduct());
			break;
		case 1:
			rc = model1.getDescription().compareTo(model2.getDescription());
			break;
		case 2:
			rc = model1.getProductCategory().compareTo(model2.getProductCategory());
			break;
		case 3:
			rc = model1.getTimeCreated().compareTo(model2.getTimeCreated());
			break;
		case 4:
			rc = model1.getUser().compareTo(model2.getUser());
			break;
		default:
			rc = 0;
		}
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}
}
