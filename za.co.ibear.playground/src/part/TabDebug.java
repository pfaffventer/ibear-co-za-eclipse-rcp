package part;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;
import java.util.List;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

import za.co.ibear.data.unit.categoryquery.CategoryQueryModel;
import za.co.ibear.data.unit.product.ProductModel;
import za.co.ibear.data.unit.stockquery.StockQueryModel;
import za.co.ibear.data.unit.swt.browse.UnitBrowseCombo;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

public class TabDebug {
	
//	private ProductLink data = new ProductLink();


	@Inject
	public TabDebug() throws Exception {
		//TODO Your code here
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) throws Exception {
		parent.setLayout(new GridLayout(1, false));

		UnitBrowseCombo stock_query = new UnitBrowseCombo(parent,1110,"Stock Query","StockQuery",false,null,false);
		stock_query.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		stock_query.addPropertyChangeListener("selection_changed",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				@SuppressWarnings("unchecked")
				List<TableItem> selection = (List<TableItem>) e.getNewValue();
				for(TableItem item:selection) {
					StockQueryModel m = (StockQueryModel) item.getData();
					p(" >>: " + m.getDescription());
				}
			}
		});
		

		UnitBrowseCombo product_browse = new UnitBrowseCombo(parent,1110,"Product","Product",false,null,false);
		product_browse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		product_browse.addPropertyChangeListener("selection_changed",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				@SuppressWarnings("unchecked")
				List<TableItem> selection = (List<TableItem>) e.getNewValue();
				for(TableItem item:selection) {
					ProductModel m = (ProductModel) item.getData();
					p(" >>: " + m.getDescription());
				}
			}
		});
		
		UnitBrowseCombo categrory_query = new UnitBrowseCombo(parent,1110,"Category Query","CategoryQuery",false,null,false);
		categrory_query.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		categrory_query.addPropertyChangeListener("selection_changed",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				@SuppressWarnings("unchecked")
				List<TableItem> selection = (List<TableItem>) e.getNewValue();
				for(TableItem item:selection) {
					CategoryQueryModel m = (CategoryQueryModel) item.getData();
					p(" >>: " + m.getDescription());
				}
			}
		});

//		BCombo combo = new BCombo(parent,370,"Description","Description",new ArrayList<String>(data.toDistinctColumnArray("Description")),BComboConstant.SINGLE);
//		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}

	
	
}