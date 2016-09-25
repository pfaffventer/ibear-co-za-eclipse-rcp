package part;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import za.co.ibear.data.unit.purchaseorder.PurchaseOrderModel;
import za.co.ibear.data.unit.swt.purchaseorder.PurchaseOrderEditor;
import za.co.ibear.data.unit.swt.purchaseorderline.PurchaseOrderLineEditor;
import za.co.ibear.swt.control.document.UnitDocument;

public class TabPurchaseOrderEditor {

	private PurchaseOrderEditor purchaseOrderEditor = null;
	private PurchaseOrderLineEditor purchaseOrderLineEditor = null;
	
	@Inject
	public TabPurchaseOrderEditor() {
		//TODO Your code here
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) throws Exception {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		UnitDocument unitDocument = new UnitDocument(parent, SWT.NONE, new int[] {350,650});

		purchaseOrderEditor = new PurchaseOrderEditor(unitDocument.getHeader(), SWT.NONE, new int[] {150,600,250},150);
		purchaseOrderEditor.addPropertyChangeListener("selected",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				PurchaseOrderModel selection = (PurchaseOrderModel) e.getNewValue();
				purchaseOrderLineEditor.selectDetail(selection.getUnitSequence());
			}
		});

		purchaseOrderLineEditor = new PurchaseOrderLineEditor(unitDocument.getDetailFolder(), SWT.NONE, new int[] {150,600,250},150);

		CTabItem tbtmStockItems = new CTabItem(unitDocument.getDetailFolder(), SWT.NONE);
		tbtmStockItems.setText("Stock Items");
		tbtmStockItems.setControl(purchaseOrderLineEditor);

	}
	
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}

}