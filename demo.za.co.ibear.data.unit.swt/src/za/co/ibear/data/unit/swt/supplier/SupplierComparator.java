package za.co.ibear.data.unit.swt.supplier;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import za.co.ibear.data.unit.supplier.SupplierModel;
public class SupplierComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;
	public SupplierComparator() {
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
		SupplierModel model1 = (SupplierModel) e1;
		SupplierModel model2 = (SupplierModel) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = model1.getSupplier().compareTo(model2.getSupplier());
			break;
		case 1:
			rc = model1.getName().compareTo(model2.getName());
			break;
		case 2:
			rc = model1.getAddress1().compareTo(model2.getAddress1());
			break;
		case 3:
			rc = model1.getAddress2().compareTo(model2.getAddress2());
			break;
		case 4:
			rc = model1.getAddress3().compareTo(model2.getAddress3());
			break;
		case 5:
			rc = model1.getAddress4().compareTo(model2.getAddress4());
			break;
		case 6:
			rc = model1.getAddress5().compareTo(model2.getAddress5());
			break;
		case 7:
			rc = model1.getUser().compareTo(model2.getUser());
			break;
		case 8:
			rc = model1.getTimeCreated().compareTo(model2.getTimeCreated());
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
