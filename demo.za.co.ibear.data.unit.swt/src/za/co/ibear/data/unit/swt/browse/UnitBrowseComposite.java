package za.co.ibear.data.unit.swt.browse;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.eclipse.swt.widgets.Composite;
public class UnitBrowseComposite extends Composite {
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	public UnitBrowseComposite(Composite parent, int style) {
		super(parent, style);
	}
	protected void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}
	protected void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}
	protected void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}
	protected void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}
	protected void firePropertyChange(String propertyName, Object oldValue,	Object newValue) {
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	protected void firePropertyChange(String propertyName, Object value) {
		changeSupport.firePropertyChange(propertyName, value, null);
	}
}
