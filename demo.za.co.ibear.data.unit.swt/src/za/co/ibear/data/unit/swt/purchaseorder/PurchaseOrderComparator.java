package za.co.ibear.data.unit.swt.purchaseorder;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import za.co.ibear.data.unit.purchaseorder.PurchaseOrderModel;
public class PurchaseOrderComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;
	public PurchaseOrderComparator() {
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
		PurchaseOrderModel model1 = (PurchaseOrderModel) e1;
		PurchaseOrderModel model2 = (PurchaseOrderModel) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = model1.getPurchaseOrder().compareTo(model2.getPurchaseOrder());
			break;
		case 1:
			rc = model1.getSupplier().compareTo(model2.getSupplier());
			break;
		case 2:
			rc = model1.getName().compareTo(model2.getName());
			break;
		case 3:
			rc = model1.getUser().compareTo(model2.getUser());
			break;
		case 4:
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
