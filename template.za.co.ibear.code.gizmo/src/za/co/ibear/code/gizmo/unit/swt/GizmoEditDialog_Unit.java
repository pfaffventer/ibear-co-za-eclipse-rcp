package za.co.ibear.code.gizmo.unit.swt;

import za.co.ibear.code.gizmo.GizmoUnitEditCombo;

public class GizmoEditDialog_Unit extends GizmoUnitEditCombo {

	public GizmoEditDialog_Unit() throws Exception {
		super("UnitEditDialog",GizmoUnitEditorConstant.PACKAGE_PREFIX,GizmoUnitEditorConstant.PATH_PREFIX);

		content = content + "import java.beans.PropertyChangeEvent;\n";
		content = content + "import java.beans.PropertyChangeListener;\n";
		content = content + "import java.beans.PropertyChangeSupport;\n";
		content = content + "import java.lang.reflect.Constructor;\n";
		content = content + "import org.eclipse.jface.dialogs.Dialog;\n";
		content = content + "import org.eclipse.swt.SWT;\n";
		content = content + "import org.eclipse.swt.events.DisposeEvent;\n";
		content = content + "import org.eclipse.swt.events.DisposeListener;\n";
		content = content + "import org.eclipse.swt.graphics.Point;\n";
		content = content + "import org.eclipse.swt.graphics.Rectangle;\n";
		content = content + "import org.eclipse.swt.widgets.Composite;\n";
		content = content + "import org.eclipse.swt.widgets.Control;\n";
		content = content + "import org.eclipse.swt.widgets.Shell;\n";
		content = content + "import za.co.ibear.code.gizmo.unit.swt.GizmoUnitEditorConstant;\n";
		content = content + "import org.eclipse.swt.layout.FillLayout;\n";
		content = content + "public class UnitEditDialog extends Dialog {\n";
		content = content + "	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);\n";
		content = content + "	private Composite container;\n";
		content = content + "	private int width = 777;\n";
		content = content + "	private Composite relativeParent;\n";
		content = content + "	private String unitName;\n";
		content = content + "	private Object selection;\n";
		content = content + "	public UnitEditDialog(Shell parentShell,int width,Composite relativeParent,String unitName) {\n";
		content = content + "		super(parentShell);\n";
		content = content + "		this.width = width;\n";
		content = content + "		this.relativeParent = relativeParent;\n";
		content = content + "		this.unitName = unitName;\n";
		content = content + "	}\n";
		content = content + "	@SuppressWarnings({ rawtypes, unchecked })\n";
		content = content + "	@Override\n";
		content = content + "	protected Control createDialogArea(Composite parent) {\n";
		content = content + "		container = (Composite) super.createDialogArea(parent);\n";
		content = content + "		container.setLayout(new FillLayout(SWT.HORIZONTAL));\n";
		content = content + "		parent.getShell().setText(Edit / Select);\n";
		content = content + "		\n";
		content = content + "		try {\n";
		content = content + "			String name = GizmoUnitEditorConstant.PACKAGE_PREFIX + unitName.toLowerCase() + . + unitName + Editor;\n";
		content = content + "			Class dialog = Class.forName(name);\n";
		
		content = content + "			Constructor constructor = dialog.getConstructor(Composite.class,int.class,int[].class,int.class);\n";
		content = content + "			int[] w = {100,600,300};\n";
		content = content + "			UnitEditComposite editor = (UnitEditComposite) constructor.newInstance(container,SWT.NONE,w,150);\n";			
		
		content = content + "			editor.addDisposeListener(new DisposeListener() {\n";
		content = content + "				public void widgetDisposed(DisposeEvent e) {\n";
		content = content + "					firePropertyChange(close, null,selection);\n";
		content = content + "				}\n";
		content = content + "			});\n";
		content = content + "			editor.addPropertyChangeListener(close,new PropertyChangeListener() {\n";
		content = content + "				public void propertyChange(final PropertyChangeEvent e) {\n";
		content = content + "					close();\n";
		content = content + "				}\n";
		content = content + "			});\n";
		content = content + "			editor.addPropertyChangeListener(selected,new PropertyChangeListener() {\n";
		content = content + "				public void propertyChange(final PropertyChangeEvent e) {\n";
		content = content + "					selection = e.getNewValue();\n";
		content = content + "				}\n";
		content = content + "			});\n";
		content = content + "			editor.addPropertyChangeListener(exit,new PropertyChangeListener() {\n";
		content = content + "				public void propertyChange(final PropertyChangeEvent e) {\n";
		content = content + "					selection = null;\n";
		content = content + "					firePropertyChange(exit, OPEN,CLOSE);\n";
		content = content + "					close();\n";
		content = content + "				}\n";
		content = content + "			});\n";
		content = content + "		} catch (Exception e) {\n";
		content = content + "			e.printStackTrace();\n";
		content = content + "		}\n";
		content = content + "		getShell().setSize(width,470);\n";
		content = content + "		setDialogLocation();\n";
		content = content + "		return container;\n";
		content = content + "	}\n";
		content = content + "	private void setDialogLocation() {\n";
		content = content + "		getShell().setLocation(relativeParent.toDisplay(new Point(0,0)));\n";
		content = content + "		Rectangle dialog = getShell().getBounds();\n";
		content = content + "		Rectangle display = getShell().getDisplay().getBounds();\n";
		content = content + "		int y_adjust = display.height - (dialog.y + dialog.height);\n";
		content = content + "		int x_ajust = display.width - (dialog.x + dialog.width);\n";
		content = content + "		int x = dialog.x;\n";
		content = content + "		int y = dialog.y;\n";
		content = content + "		if(y_adjust<0) {\n";
		content = content + "			y = (y + y_adjust)-20;\n";
		content = content + "		}\n";
		content = content + "		if(x_ajust<0) {\n";
		content = content + "			x = (x + x_ajust)-20;\n";
		content = content + "		}\n";
		content = content + "		getShell().setLocation(x,y);\n";
		content = content + "	}\n";
		content = content + "	protected void setShellStyle(int arg0) {\n";
		content = content + "		super.setShellStyle(SWT.APPLICATION_MODAL | SWT.TOOL |  SWT.CLOSE | SWT.RESIZE);\n";
		content = content + "	}\n";
		content = content + "	@Override\n";
		content = content + "	protected void createButtonsForButtonBar(Composite parent) {\n";
		content = content + "	}\n";
		content = content + "	public void addPropertyChangeListener(PropertyChangeListener listener) {\n";
		content = content + "		changeSupport.addPropertyChangeListener(listener);\n";
		content = content + "	}\n";
		content = content + "	public void removePropertyChangeListener(PropertyChangeListener listener) {\n";
		content = content + "		changeSupport.removePropertyChangeListener(listener);\n";
		content = content + "	}\n";
		content = content + "	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {\n";
		content = content + "		changeSupport.addPropertyChangeListener(propertyName, listener);\n";
		content = content + "	}\n";
		content = content + "	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {\n";
		content = content + "		changeSupport.removePropertyChangeListener(propertyName, listener);\n";
		content = content + "	}\n";
		content = content + "	protected void firePropertyChange(String propertyName, Object oldValue,	Object newValue) {\n";
		content = content + "		changeSupport.firePropertyChange(propertyName, oldValue, newValue);\n";
		content = content + "	}\n";
		content = content + "	protected void firePropertyChange(String propertyName, Object value) {\n";
		content = content + "		changeSupport.firePropertyChange(propertyName, value, null);\n";
		content = content + "	}\n";
		content = content + "	@Override\n";
		content = content + "	protected Point getInitialSize() {\n";
		content = content + "		return new Point(450, 300);\n";
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

