package za.co.ibear.swt.control.combo;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class BFilter extends ViewerFilter {
	
	private String searchString;

	public void setSearchText(String s) {
		this.searchString = ".*" + s + ".*";
	}

	@Override
	public boolean select(Viewer viewer, 
			Object parentElement, 
			Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		String entry = (String) element;
		if (entry.toLowerCase().matches(searchString.toLowerCase())) {
			return true;
		}
		return false;
	}

}
