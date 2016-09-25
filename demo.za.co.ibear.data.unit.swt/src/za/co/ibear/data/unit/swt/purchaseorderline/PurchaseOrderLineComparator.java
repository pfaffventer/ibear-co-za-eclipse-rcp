package za.co.ibear.data.unit.swt.purchaseorderline;

import org.eclipse.jface.viewers.Viewer;

import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import za.co.ibear.data.unit.purchaseorderline.PurchaseOrderLineModel;

public class PurchaseOrderLineComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;
	public PurchaseOrderLineComparator() {
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
		PurchaseOrderLineModel model1 = (PurchaseOrderLineModel) e1;
		PurchaseOrderLineModel model2 = (PurchaseOrderLineModel) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = model1.getProduct().compareTo(model2.getProduct());
			break;
		case 1:
			rc = model1.getDescription().compareTo(model2.getDescription());
			break;
		case 2:
			if(model1.getVolume()>model2.getVolume()) {
				rc = 1;
			} else {
				rc = -1;
			}
			break;
		case 3:
			if(model1.getPrice()>model2.getPrice()) {
				rc = 1;
			} else {
				rc = -1;
			}
			break;
		case 4:
			if(model1.getTotal()>model2.getTotal()) {
				rc = 1;
			} else {
				rc = -1;
			}
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
