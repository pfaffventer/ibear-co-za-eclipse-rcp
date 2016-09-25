package za.co.ibear.code.gizmo.unit.swt;

import za.co.ibear.code.gizmo.GizmoUnitEditCombo;

public class GizmoEditCombo_Unit extends GizmoUnitEditCombo {

	public GizmoEditCombo_Unit() throws Exception {
		super("UnitEditCombo",GizmoUnitEditorConstant.PACKAGE_PREFIX,GizmoUnitEditorConstant.PATH_PREFIX);

		content = content + "import java.beans.PropertyChangeEvent;\n";
		content = content + "import java.beans.PropertyChangeListener;\n";
		content = content + "import java.lang.reflect.Field;\n";
		content = content + "import org.eclipse.swt.widgets.Composite;\n";
		content = content + "import za.co.ibear.swt.control.combo.BCombo;\n";
		content = content + "import za.co.ibear.swt.control.combo.BComboConstant;\n";
		content = content + "public class UnitEditCombo extends BCombo {\n";
		content = content + "	private UnitEditDialog editDialog;\n";
		content = content + "	protected Object selectedModel;\n";
		content = content + "	private String selection = null;\n";
		content = content + "	private String columnName = null;\n";
		content = content + "	public UnitEditCombo(Composite parent,int width,String text,String columnName) throws Exception {\n";
		content = content + "		super(parent,width,text,columnName,null,BComboConstant.UNIT_EDIT);\n";
		content = content + "		editDialog = new UnitEditDialog(parent.getShell(),width,this,columnName);\n";
		content = content + "		this.setColumnName(columnName);\n";
		content = content + "		editDialog.addPropertyChangeListener(close,new PropertyChangeListener() {\n";
		content = content + "			public void propertyChange(final PropertyChangeEvent e) {\n";
		content = content + "				if(e.getNewValue()!=null) {\n";
		content = content + "					selectedModel = e.getNewValue();\n";
		content = content + "				}\n";
		content = content + "			}\n";
		content = content + "		});\n";
		content = content + "		editDialog.addPropertyChangeListener(exit,new PropertyChangeListener() {\n";
		content = content + "			public void propertyChange(final PropertyChangeEvent e) {\n";
		content = content + "				selectedModel = null;\n";
		content = content + "			}\n";
		content = content + "		});\n";
		content = content + "	}\n";
		content = content + "	public String getUnitSelection() {\n";
		content = content + "		return selection;\n";
		content = content + "	}\n";
		content = content + "	@Override\n";
		content = content + "	protected void filterVisible() {\n";
		content = content + "		editDialog.open();\n";
		content = content + "		if(selectedModel==null) {\n";
		content = content + "			getLabel().setText();\n";
		content = content + "		} else {\n";
		content = content + "			select();\n";
		content = content + "		}\n";
		content = content + "	}\n";
		content = content + "	private void select() {\n";
		content = content + "		Field[] fields = selectedModel.getClass().getDeclaredFields();\n";
		content = content + "		for (Field field : fields) {\n";
		content = content + "			field.setAccessible(true);\n";
		content = content + "			if(field.getType().toString().equals(class java.lang.String)) {\n";
		content = content + "				if(field.getName().trim().equals(columnName.trim().toLowerCase())) {\n";
		content = content + "					try {\n";
		content = content + "						getLabel().setText((String) field.get(selectedModel));\n";
		content = content + "					} catch (Exception e1) {\n";
		content = content + "						e1.printStackTrace();\n";
		content = content + "					}\n";
		content = content + "				}\n";
		content = content + "			}\n";
		content = content + "		}\n";
		content = content + "		firePropertyChange(do-refresh, EDIT_COMBO, selectedModel);\n";
		content = content + "	}\n";
		content = content + "	public String getColumnName() {\n";
		content = content + "		return columnName;\n";
		content = content + "	}\n";
		content = content + "	public void setColumnName(String columnName) {\n";
		content = content + "		this.columnName = columnName;\n";
		content = content + "	}\n";
		content = content + "	protected void p(String v) {\n";
		content = content + "		System.out.println(this.getClass().getName() + :) + v);\n";
		content = content + "	}\n";
		content = content + "}\n";

		createFile();		

	}
	
	@Override
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":) " + v);
	}

}

