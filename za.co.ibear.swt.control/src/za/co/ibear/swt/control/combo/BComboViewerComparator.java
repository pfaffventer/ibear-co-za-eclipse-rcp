package za.co.ibear.swt.control.combo;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

public class BComboViewerComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public BComboViewerComparator() {
		this.propertyIndex = 0;
		
		System.out.println("BComboViewerComparator");
		
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
		BCombo entry01 = (BCombo) e1;
		BCombo entry02 = (BCombo) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = entry01.getText().compareTo(entry02.getText());
			break;
		default:
			rc = 0;
		}
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}

} 