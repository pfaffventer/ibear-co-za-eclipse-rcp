package za.co.ibear.data.unit.swt.browse;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import za.co.ibear.code.gizmo.unit.swt.GizmoUnitEditorConstant;
public class UnitBrowseDialog extends Dialog {
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private Composite container;
	private int width = 777;
	private Composite relativeParent;
	private String unitName;
	private UnitBrowseDialogState state = new UnitBrowseDialogState();
	public void setState(UnitBrowseDialogState state) {
		this.state = state;
	}
	private Map<String,UnitBrowseDialogState> unitBorwseStateMap = new HashMap<String,UnitBrowseDialogState>();
	private boolean SINGLE_SELECT = false;
	
	private List<String> initFilter = new ArrayList<String>();
	
	private Map<String,TreeSet<String>> selection = new HashMap<String,TreeSet<String>>();
	
	public UnitBrowseDialog(Shell parentShell,int width,Composite relativeParent,String unitName,boolean SINGLE_SELECT,List<String> initFilter) {
		super(parentShell);
		this.width = width;
		this.relativeParent = relativeParent;
		this.setUnitName(unitName);
		this.setInitFilter(initFilter);
		this.SINGLE_SELECT = SINGLE_SELECT;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Control createDialogArea(Composite parent) {
		container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		parent.getShell().setText("Select");
		try {
			String name = GizmoUnitEditorConstant.PACKAGE_PREFIX + unitName.toLowerCase() + "." + unitName + "Browser";
			Class dialog = Class.forName(name);
			Constructor constructor = dialog.getConstructor(Composite.class,int.class,UnitBrowseDialogState.class,Map.class,boolean.class,List.class,Map.class);
			UnitBrowseComposite browser = (UnitBrowseComposite) constructor.newInstance(container,SWT.NONE,state,unitBorwseStateMap,this.SINGLE_SELECT,this.getInitFilter(),this.selection);
			browser.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					firePropertyChange("select",null,state);
				}
			});
			browser.addPropertyChangeListener("selection_changed",new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
					firePropertyChange("selection_changed",null,e.getNewValue());
				}
			});
			browser.addPropertyChangeListener("save_unit_browse_state",new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
					unitBorwseStateMap = null;
					unitBorwseStateMap = (Map<String,UnitBrowseDialogState>) e.getNewValue();
				}
			});
			browser.addPropertyChangeListener("save_state",new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
					saveState((UnitBrowseDialogState) e.getNewValue());
				}
			});
			browser.addPropertyChangeListener("close",new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
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
	public void saveState(UnitBrowseDialogState stateToSave) {
		state = null;
		state = stateToSave;
		state.setUnitName(unitName);
	}
	public UnitBrowseDialogState getState() {
		return state;
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
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Map<String,UnitBrowseDialogState> getUnitBorwseStateMap() {
		return unitBorwseStateMap;
	}
	public void setUnitBorwseStateMap(Map<String,UnitBrowseDialogState> unitBorwseStateMap) {
		this.unitBorwseStateMap = unitBorwseStateMap;
	}
	public List<String> getInitFilter() {
		return initFilter;
	}
	public void setInitFilter(List<String> initFilter) {
		this.initFilter = initFilter;
	}
	public Map<String, TreeSet<String>> getSelection() {
		return selection;
	}
	public void setSelection(Map<String, TreeSet<String>> selection) {
		this.selection = selection;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getName() + ":) "+ v);
	}
}
