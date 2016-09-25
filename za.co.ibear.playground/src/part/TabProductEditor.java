package part;

import javax.inject.Inject;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import za.co.ibear.data.unit.swt.product.ProductEditor;


public class TabProductEditor {
	@Inject
	public TabProductEditor() {
		//TODO Your code here
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) throws Exception {

		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		new ProductEditor(parent, SWT.NONE, new int[] {150,600,250},130);

	}

}