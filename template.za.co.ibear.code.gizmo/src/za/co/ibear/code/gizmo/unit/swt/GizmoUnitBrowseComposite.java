package za.co.ibear.code.gizmo.unit.swt;

import za.co.ibear.code.gizmo.GizmoUnitBrowse;

public class GizmoUnitBrowseComposite extends GizmoUnitBrowse {

	public GizmoUnitBrowseComposite() throws Exception {
		super("UnitBrowseComposite",GizmoUnitEditorConstant.PACKAGE_PREFIX,GizmoUnitEditorConstant.PATH_PREFIX);

		content = content + "import java.beans.PropertyChangeListener;\n";
		content = content + "import java.beans.PropertyChangeSupport;\n";
		content = content + "import org.eclipse.swt.widgets.Composite;\n";
		content = content + "public class UnitBrowseComposite extends Composite {\n";
		content = content + "	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);\n";
		content = content + "	public UnitBrowseComposite(Composite parent, int style) {\n";
		content = content + "		super(parent, style);\n";
		content = content + "	}\n";
		content = content + "	protected void addPropertyChangeListener(PropertyChangeListener listener) {\n";
		content = content + "		changeSupport.addPropertyChangeListener(listener);\n";
		content = content + "	}\n";
		content = content + "	protected void removePropertyChangeListener(PropertyChangeListener listener) {\n";
		content = content + "		changeSupport.removePropertyChangeListener(listener);\n";
		content = content + "	}\n";
		content = content + "	protected void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {\n";
		content = content + "		changeSupport.addPropertyChangeListener(propertyName, listener);\n";
		content = content + "	}\n";
		content = content + "	protected void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {\n";
		content = content + "		changeSupport.removePropertyChangeListener(propertyName, listener);\n";
		content = content + "	}\n";
		content = content + "	protected void firePropertyChange(String propertyName, Object oldValue,	Object newValue) {\n";
		content = content + "		changeSupport.firePropertyChange(propertyName, oldValue, newValue);\n";
		content = content + "	}\n";
		content = content + "	protected void firePropertyChange(String propertyName, Object value) {\n";
		content = content + "		changeSupport.firePropertyChange(propertyName, value, null);\n";
		content = content + "	}\n";
		content = content + "}\n";

		createFile();		
	}
	
	@Override
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":) " + v);
	}

}

