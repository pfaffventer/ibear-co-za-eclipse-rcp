package za.co.ibear.swt.control.combo;

import java.beans.PropertyChangeEvent;



import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.wb.swt.SWTResourceManager;


public class BCombo extends Composite {

	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	private BComboSingleDialog singleDialog;
	private BComboMultiDialog multiDialog;
	private BComboDateDialog dateDialog;

	private int width = 100;

	protected Label label;
	protected Label indicator;
	private String text = null;
	private String columnName = null;
	private int type = 0;
	private Label spacer;

	public BCombo(Composite parent,int width,String text,String columnName,List<String> items,int type) {
		super(parent, SWT.BORDER);
		this.setWidth(width);
		this.setText(text);
		this.setColumnName(columnName);
		setType(type);
		switch (getType()) {
		case BComboConstant.SINGLE:
			singleDialog = new BComboSingleDialog(parent.getShell(),items,width,this);
			singleDialog.addPropertyChangeListener("value",new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
					if(e.getNewValue().toString().equals("NONE")) {
						label.setText("");
					} else {
						label.setText(e.getNewValue().toString());
					}
					firePropertyChange("do-refresh", null, label);
				}
			});
			break;
		case BComboConstant.MULTI:
			multiDialog = new BComboMultiDialog(parent.getShell(),items,width,this);
			multiDialog.addPropertyChangeListener("select",new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {

					//FIXME enable background refresh via property change

				}
			});
			break;
		case BComboConstant.DATE:
			dateDialog = new BComboDateDialog(parent.getShell(),this);
			dateDialog.addPropertyChangeListener("select",new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {

					//FIXME enable background refresh via property change

				}
			});
			break;
		default:break;
		}

		GridLayout gridLayout = new GridLayout(3, false);

		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		setLayout(gridLayout);
		GridData gd_value = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_value.heightHint = 23;
		
		spacer = new Label(this, SWT.NONE);
		spacer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				filterVisible();
			}
		});
		spacer.setText(" ");

		label = new Label(this, SWT.NONE);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				filterVisible();
			}
		});

		getLabel().setText(text);

		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		indicator = new Label(this, SWT.NONE);
		indicator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				filterVisible();
			}
		});
		indicator.setText("> ");
		indicator.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		indicator.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
	}

	protected void filterVisible() {
		switch (getType()) {
		case BComboConstant.SINGLE:
			singleDialog.open();
			break;
		case BComboConstant.MULTI:
			multiDialog.open();
			if(multiDialog.getSelection().size()>0) {
				getIndicator().setText("# ");
			} else {
				getIndicator().setText("> ");
			}
			firePropertyChange("close", "OPEN", "CLOSE");
			break;
		case BComboConstant.DATE:
			dateDialog.open();
			if(dateDialog.getSelection().size()>0) {
				getIndicator().setText("# ");
			} else {
				getIndicator().setText("> ");
			}
			if(dateDialog.getSelection().size()>0) {
				getLabel().setToolTipText("From: '" + dateDialog.getSelection().get("from") + "', To: '" + dateDialog.getSelection().get("to") +"'");
			} else {
				getLabel().setToolTipText("");
			}
			firePropertyChange("close", "OPEN", "CLOSE");
			break;
		default:break;
		}
	}

	public void clearUnitBrowse() {
	}
	
	public List<String> getItems() {
		switch (getType()) {
		case BComboConstant.MULTI: return multiDialog.getItems();
		case BComboConstant.SINGLE: return singleDialog.getItems();
		default: return null;
		}
	}

	public void setItems(List<String> items) {
		switch (getType()) {
		case BComboConstant.SINGLE:
			singleDialog.setItems(items);
			break;
		case BComboConstant.MULTI:
			multiDialog.setItems(items);
			break;
		default:break;
		}
	}

	public Table getDataTable() {
		switch (getType()) {
		case BComboConstant.MULTI: return multiDialog.getDataTable();
		case BComboConstant.SINGLE: return singleDialog.getDataTable();
		default: return null;
		}
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Map<String,Date> getDateSelection() {
		switch (getType()) {
		case BComboConstant.DATE: return dateDialog.getSelection();
		default: return null;
		}
	}

	public Set<String> getSelection() {
		switch (getType()) {
		case BComboConstant.MULTI: 
			return multiDialog.getSelection();
		case BComboConstant.UNIT_BROWSE:
			getBrowseSelection();
			return null;
		
		default: return null;
		}
	}

	public Map<String,TreeSet<String>> getBrowseSelection() {
		return null;
	}

	public void setBrowseSelection(Map<String,TreeSet<String>> selection) {
	}

	public void setSelection(Set<String> selection) {
		switch (getType()) {
		case BComboConstant.MULTI:
			if(selection.size()>0) {
				multiDialog.setSelection(selection);
				getIndicator().setText("# ");
			}
			break;
		default:break;
		}
	}

	public void seDatetSelection(Map<String,Date> selection) {
		switch (getType()) {
		case BComboConstant.DATE:
			if(selection.size()>0) {
				dateDialog.setSelection(selection);
				getIndicator().setText("# ");
			}
			break;
		default:break;
		}
	}

	public void setAllSelected() {
		switch (getType()) {
		case BComboConstant.MULTI: multiDialog.setAllSelected(false);break;
		default: break;
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,	Object newValue) {
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected void firePropertyChange(String propertyName, Object value) {
		changeSupport.firePropertyChange(propertyName, value, null);
	}

	protected void p(String v) {
		System.out.println(this.getClass().getName() + ":) "+ v);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		firePropertyChange("allSelected", this.type, this.type = type);
	}

	public BComboDateDialog getDatedDialog() {
		return dateDialog;
	}

	public void setDatedDialog(BComboDateDialog datedDialog) {
		this.dateDialog = datedDialog;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Label getIndicator() {
		return indicator;
	}

	public void setIndicator(Label indicator) {
		this.indicator = indicator;
	}
	
	public void setFilter(Map<String, List<String>> filter) {
	}



	public void crearState() {
		
	}
}

