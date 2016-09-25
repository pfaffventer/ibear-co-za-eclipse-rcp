package za.co.ibear.data.unit.swt.browse;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

import za.co.ibear.swt.control.combo.BCombo;
import za.co.ibear.swt.control.combo.BComboConstant;

public class UnitBrowseCombo extends BCombo {
	private UnitBrowseDialog browseDialog;
	private String columnName = null;
	private UnitBrowseDialogState state = new UnitBrowseDialogState();
	private String label = null;
	
	protected Object selectedModel;
	protected boolean IS_FILTER = false;
	public UnitBrowseCombo(Composite parent,int width,String text,String columnName,final boolean SINGLE_SELECT,List<String> initFilter,boolean IS_FILTER) throws Exception {
		super(parent,width,text,columnName,null,BComboConstant.UNIT_BROWSE);
		this.columnName = columnName;
		this.IS_FILTER = IS_FILTER;
		browseDialog = new UnitBrowseDialog(parent.getShell(),width,this,columnName,SINGLE_SELECT,initFilter);
		browseDialog.addPropertyChangeListener("selection_changed",new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent e) {
				if(e.getNewValue()!=null) {
					if(SINGLE_SELECT) {
						if(e.getNewValue()!=null) {
						@SuppressWarnings("unchecked")
						List<TableItem> selection = ((List<TableItem>) e.getNewValue());
							if(selection.size()>0) {
								selectedModel = selection.get(0).getData();
								label = selection.get(0).getText(0);
							} else {
								label = null;
							}
						}
					}
					firePropertyChange("selection_changed",null,e.getNewValue());
				}
			}
		});
		browseDialog.addPropertyChangeListener("select",new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent e) {
				setState((UnitBrowseDialogState) e.getNewValue());
				firePropertyChange("select",null,e);
			}
		});
	}
	@Override
	protected void filterVisible() {
		browseDialog.open();
		if(browseDialog.getState().getSavedSelectionIndex().length>0) {
			getIndicator().setText("# ");
			if(label!=null) {
				if(!IS_FILTER) {
					getLabel().setText(label);
				}
			}
		} else {
			getIndicator().setText("> ");
		}
		firePropertyChange("do-refresh", "EDIT_COMBO", selectedModel);
	}
	@Override
	public Map<String,TreeSet<String>> getBrowseSelection() {
		return state.getSelection();
	}
	@Override
	public void setBrowseSelection(Map<String,TreeSet<String>> selection) {
		state.setSelection(selection);
	}
	@Override
	public void clearUnitBrowse() {
		state.setSavedSelectionIndex(null);
	}
	@Override
	public void crearState() {
		setState(new UnitBrowseDialogState());
		browseDialog.setState(getState());
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public UnitBrowseDialogState getState() {
		return state;
	}
	public void setState(UnitBrowseDialogState state) {
		this.state = state;
	}
	public UnitBrowseDialog getBrowseDialog() {
		return browseDialog;
	}
	public void setBrowseDialog(UnitBrowseDialog browseDialog) {
		this.browseDialog = browseDialog;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getName() + ":) "+ v);
	}
	
}
