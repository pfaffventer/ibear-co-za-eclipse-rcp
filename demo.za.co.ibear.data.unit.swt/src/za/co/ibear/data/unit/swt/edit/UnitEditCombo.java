package za.co.ibear.data.unit.swt.edit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import org.eclipse.swt.widgets.Composite;
import za.co.ibear.swt.control.combo.BCombo;
import za.co.ibear.swt.control.combo.BComboConstant;
public class UnitEditCombo extends BCombo {
	private UnitEditDialog editDialog;
	protected Object selectedModel;
	private String selection = null;
	private String columnName = null;
	public UnitEditCombo(Composite parent,int width,String text,String columnName) throws Exception {
		super(parent,width,text,columnName,null,BComboConstant.UNIT_EDIT);
		editDialog = new UnitEditDialog(parent.getShell(),width,this,columnName);
		this.setColumnName(columnName);
		editDialog.addPropertyChangeListener("close",new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent e) {
				if(e.getNewValue()!=null) {
					selectedModel = e.getNewValue();
				}
			}
		});
		editDialog.addPropertyChangeListener("exit",new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent e) {
				selectedModel = null;
			}
		});
	}
	public String getUnitSelection() {
		return selection;
	}
	@Override
	protected void filterVisible() {
		editDialog.open();
		if(selectedModel==null) {
			getLabel().setText("");
		} else {
			select();
		}
	}
	private void select() {
		Field[] fields = selectedModel.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if(field.getType().toString().equals("class java.lang.String")) {
				if(field.getName().trim().equals(columnName.trim().toLowerCase())) {
					try {
						getLabel().setText((String) field.get(selectedModel));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		firePropertyChange("do-refresh", "EDIT_COMBO", selectedModel);
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getName() + ":) "+ v);
	}
}
