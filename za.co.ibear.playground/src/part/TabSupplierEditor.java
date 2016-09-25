 
package part;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import za.co.ibear.data.unit.swt.supplier.SupplierEditor;


public class TabSupplierEditor {
	@Inject
	public TabSupplierEditor() {
		//TODO Your code here
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) throws Exception {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
	
		int[] w = {100,600,300};
		new SupplierEditor(parent,SWT.NONE,w,200);
		
	
	}
	
	
	
	
}