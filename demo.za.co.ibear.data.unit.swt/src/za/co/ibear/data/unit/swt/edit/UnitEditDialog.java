package za.co.ibear.data.unit.swt.edit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Constructor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import za.co.ibear.code.gizmo.unit.swt.GizmoUnitEditorConstant;
import org.eclipse.swt.layout.FillLayout;
public class UnitEditDialog extends Dialog {
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private Composite container;
	private int width = 777;
	private Composite relativeParent;
	private String unitName;
	private Object selection;
	public UnitEditDialog(Shell parentShell,int width,Composite relativeParent,String unitName) {
		super(parentShell);
		this.width = width;
		this.relativeParent = relativeParent;
		this.unitName = unitName;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Control createDialogArea(Composite parent) {
		container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		parent.getShell().setText("Edit / Select");
		
		try {
			String name = GizmoUnitEditorConstant.PACKAGE_PREFIX + unitName.toLowerCase() + "." + unitName + "Editor";
			Class dialog = Class.forName(name);
			Constructor constructor = dialog.getConstructor(Composite.class,int.class,int[].class,int.class);
			int[] w = {100,600,300};
			UnitEditComposite editor = (UnitEditComposite) constructor.newInstance(container,SWT.NONE,w,150);
			editor.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					firePropertyChange("close", null,selection);
				}
			});
			editor.addPropertyChangeListener("close",new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
					close();
				}
			});
			editor.addPropertyChangeListener("selected",new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
					selection = e.getNewValue();
				}
			});
			editor.addPropertyChangeListener("exit",new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
					selection = null;
					firePropertyChange("exit", "OPEN","CLOSE");
					close();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		getShell().setSize(width,470);
		setDialogLocation();
		return container;
	}
	private void setDialogLocation() {
		getShell().setLocation(relativeParent.toDisplay(new Point(0,0)));
		Rectangle dialog = getShell().getBounds();
		Rectangle display = getShell().getDisplay().getBounds();
		int y_adjust = display.height - (dialog.y + dialog.height);
		int x_ajust = display.width - (dialog.x + dialog.width);
		int x = dialog.x;
		int y = dialog.y;
		if(y_adjust<0) {
			y = (y + y_adjust)-20;
		}
		if(x_ajust<0) {
			x = (x + x_ajust)-20;
		}
		getShell().setLocation(x,y);
	}
	protected void setShellStyle(int arg0) {
		super.setShellStyle(SWT.APPLICATION_MODAL | SWT.TOOL |  SWT.CLOSE | SWT.RESIZE);
	}
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
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
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
	protected void p(String v) {
		System.out.println(this.getClass().getName() + ":) "+ v);
	}
}
