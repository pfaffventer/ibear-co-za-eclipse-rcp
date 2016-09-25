package za.co.ibear.code.gizmo.unit.swt;

import za.co.ibear.code.gizmo.GizmoUnitBrowse;

public class GizmoUnitBrowseCombo extends GizmoUnitBrowse {

	public GizmoUnitBrowseCombo() throws Exception {
		super("UnitBrowseCombo",GizmoUnitEditorConstant.PACKAGE_PREFIX,GizmoUnitEditorConstant.PATH_PREFIX);

		content = content + "import java.beans.PropertyChangeEvent;\n";
		content = content + "import java.beans.PropertyChangeListener;\n";
		content = content + "import java.util.List;\n";
		content = content + "import java.util.Map;\n";
		content = content + "import java.util.TreeSet;\n";

		content = content + "import org.eclipse.swt.widgets.Composite;\n";
		content = content + "import org.eclipse.swt.widgets.TableItem;\n";

		content = content + "import za.co.ibear.swt.control.combo.BCombo;\n";
		content = content + "import za.co.ibear.swt.control.combo.BComboConstant;\n";
		content = content + "public class UnitBrowseCombo extends BCombo {\n";
		content = content + "	private UnitBrowseDialog browseDialog;\n";
		content = content + "	private String columnName = null;\n";
		content = content + "	private UnitBrowseDialogState state = new UnitBrowseDialogState();\n";
		content = content + "	private String label = null;\n";
		content = content + "	\n";
		content = content + "	protected Object selectedModel;\n";

		content = content + "	protected boolean IS_FILTER = false;\n";

		content = content + "	public UnitBrowseCombo(Composite parent,int width,String text,String columnName,final boolean SINGLE_SELECT,List<String> initFilter,boolean IS_FILTER) throws Exception {\n";
		content = content + "		super(parent,width,text,columnName,null,BComboConstant.UNIT_BROWSE);\n";
		content = content + "		this.columnName = columnName;\n";
		content = content + "		this.IS_FILTER = IS_FILTER;\n";
		content = content + "		browseDialog = new UnitBrowseDialog(parent.getShell(),width,this,columnName,SINGLE_SELECT,initFilter);\n";
		content = content + "		browseDialog.addPropertyChangeListener(selection_changed,new PropertyChangeListener() {\n";
		content = content + "			public void propertyChange(final PropertyChangeEvent e) {\n";
		content = content + "				if(e.getNewValue()!=null) {\n";
		content = content + "					if(SINGLE_SELECT) {\n";
		content = content + "						if(e.getNewValue()!=null) {\n";
		content = content + "						@SuppressWarnings(unchecked)\n";
		content = content + "						List<TableItem> selection = ((List<TableItem>) e.getNewValue());\n";
		content = content + "							if(selection.size()>0) {\n";
		content = content + "								selectedModel = selection.get(0).getData();\n";
		content = content + "								label = selection.get(0).getText(0);\n";
		content = content + "							} else {\n";
		content = content + "								label = null;\n";
		content = content + "							}\n";
		content = content + "						}\n";
		content = content + "					}\n";
		content = content + "					firePropertyChange(selection_changed,null,e.getNewValue());\n";
		content = content + "				}\n";
		content = content + "			}\n";
		content = content + "		});\n";
		content = content + "		browseDialog.addPropertyChangeListener(select,new PropertyChangeListener() {\n";
		content = content + "			public void propertyChange(final PropertyChangeEvent e) {\n";
		content = content + "				setState((UnitBrowseDialogState) e.getNewValue());\n";
		content = content + "				firePropertyChange(select,null,e);\n";
		content = content + "			}\n";
		content = content + "		});\n";
		content = content + "	}\n";
		content = content + "	@Override\n";
		content = content + "	protected void filterVisible() {\n";
		content = content + "		browseDialog.open();\n";
		content = content + "		if(browseDialog.getState().getSavedSelectionIndex().length>0) {\n";
		content = content + "			getIndicator().setText(# );\n";
		content = content + "			if(label!=null) {\n";
		content = content + "				if(!IS_FILTER) {\n";
		content = content + "					getLabel().setText(label);\n";
		content = content + "				}\n";
		content = content + "			}\n";
		content = content + "		} else {\n";
		content = content + "			getIndicator().setText(> );\n";
		content = content + "		}\n";
		content = content + "		firePropertyChange(do-refresh, EDIT_COMBO, selectedModel);\n";
		content = content + "	}\n";
		content = content + "	@Override\n";
		content = content + "	public Map<String,TreeSet<String>> getBrowseSelection() {\n";
		content = content + "		return state.getSelection();\n";
		content = content + "	}\n";
		content = content + "	@Override\n";
		content = content + "	public void setBrowseSelection(Map<String,TreeSet<String>> selection) {\n";
		content = content + "		state.setSelection(selection);\n";
		content = content + "	}\n";
		content = content + "	@Override\n";
		content = content + "	public void clearUnitBrowse() {\n";
		content = content + "		state.setSavedSelectionIndex(null);\n";
		content = content + "	}\n";
		content = content + "	@Override\n";
		content = content + "	public void crearState() {\n";
		content = content + "		setState(new UnitBrowseDialogState());\n";
		content = content + "		browseDialog.setState(getState());\n";
		content = content + "	}\n";
		content = content + "	public String getColumnName() {\n";
		content = content + "		return columnName;\n";
		content = content + "	}\n";
		content = content + "	public void setColumnName(String columnName) {\n";
		content = content + "		this.columnName = columnName;\n";
		content = content + "	}\n";
		content = content + "	public UnitBrowseDialogState getState() {\n";
		content = content + "		return state;\n";
		content = content + "	}\n";
		content = content + "	public void setState(UnitBrowseDialogState state) {\n";
		content = content + "		this.state = state;\n";
		content = content + "	}\n";
		content = content + "	public UnitBrowseDialog getBrowseDialog() {\n";
		content = content + "		return browseDialog;\n";
		content = content + "	}\n";
		content = content + "	public void setBrowseDialog(UnitBrowseDialog browseDialog) {\n";
		content = content + "		this.browseDialog = browseDialog;\n";
		content = content + "	}\n";
		content = content + "	protected void p(String v) {\n";
		content = content + "		System.out.println(this.getClass().getName() + :) + v);\n";
		content = content + "	}\n";
		content = content + "	\n";
		content = content + "}\n";

		createFile();		
	}
	
	@Override
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":) " + v);
	}

}

