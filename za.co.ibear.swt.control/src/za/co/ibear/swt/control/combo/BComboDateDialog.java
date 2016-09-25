package za.co.ibear.swt.control.combo;

import java.beans.PropertyChangeListener;

import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.ResourceManager;

public class BComboDateDialog extends Dialog {

	private Composite container;
	private Composite relativeParent;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	private SimpleDateFormat df_year = new SimpleDateFormat("yyyy");
	private SimpleDateFormat df_month = new SimpleDateFormat("MM");
	private SimpleDateFormat df_day = new SimpleDateFormat("dd");

	private DateTime from;
	private DateTime to;
	
	private Button clear;

	private Label lblFrom;
	private Label lblTo;
	
	private Date today = null;
	private Date dteFrom = null;
	private Date dteTo = null;

	private Map<String,Date> selection = new HashMap<String,Date>();

	public BComboDateDialog(Shell parentShell,Composite relativeParent) {
		super(parentShell);
		this.relativeParent = relativeParent;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		container = (Composite) super.createDialogArea(parent);

		today = new Date();
		
		parent.getShell().setText("Select");

		container.setLayout(new GridLayout(3, false));
		
		close = new Button(container, SWT.NONE);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				close();
			}
		});
		close.setImage(ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/tick.png"));
		close.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		close.setText("Close");
		
		from = new DateTime(container, SWT.DATE | SWT.BORDER | SWT.DROP_DOWN);
		from.setToolTipText("From date");
		from.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setFrom();
			}
		});
		GridData gd_from = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_from.widthHint = 270;
		from.setLayoutData(gd_from);

		to = new DateTime(container, SWT.DATE | SWT.BORDER | SWT.DROP_DOWN);
		to.setToolTipText("To date");
		to.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setTo();
			}
		});
		GridData gd_to = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_to.widthHint = 270;
		to.setLayoutData(gd_to);

		clear = new Button(container, SWT.NONE);
		clear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				reset();
				getShell().close();
			}
		});
		clear.setText("Clear");
		
		lblFrom = new Label(container, SWT.NONE);
		lblFrom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		lblTo = new Label(container, SWT.NONE);
		lblTo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		
		if(selection.size()==0) {
			reset();
		} else {
			Date f = selection.get("from");
			from.setYear(Integer.valueOf(df_year.format(f)));
			from.setMonth(Integer.valueOf(df_month.format(f))-1);
			from.setDay(Integer.valueOf(df_day.format(f)));
			setFrom();
			Date t = selection.get("to");
			to.setYear(Integer.valueOf(df_year.format(t)));
			to.setMonth(Integer.valueOf(df_month.format(t))-1);
			to.setDay(Integer.valueOf(df_day.format(t)));
			setTo();
		}

		getShell().setSize(370,145);
		setDialogLocation();

		return container;
	}

	protected void cancelPressed() {
		handleShellCloseEvent();
	}
	
	protected void handleShellCloseEvent() {
		if(valid()) {
			super.handleShellCloseEvent();
		} else {
			open();
			
		}
	}

	private void reset() {
		lblFrom.setText("From: <select>");
		lblTo.setText("To: <select>");
		dteFrom = null;
		dteTo = null;
		from.setYear(Integer.valueOf(df_year.format(today)));
		from.setMonth(Integer.valueOf(df_month.format(today))-1);
		from.setDay(Integer.valueOf(df_day.format(today)));
		to.setYear(Integer.valueOf(df_year.format(today)));
		to.setMonth(Integer.valueOf(df_month.format(today))-1);
		to.setDay(Integer.valueOf(df_day.format(today)));
		selection = new HashMap<String,Date>();
	}
	
	private void setFrom() {
		try {
			if(selection.containsKey("from")) {
				selection.remove("from");
			}
			selection.put("from",df.parse("" + from.getYear() + "-" + (from.getMonth()+1) + "-" + from.getDay()));
			dteFrom = new Date(df.parse("" + from.getYear() + "-" + (from.getMonth()+1) + "-" + from.getDay()).getTime());
			lblFrom.setText("From: " + df.format(dteFrom).trim());
		} catch (Exception e) {
			MessageDialog.openError(container.getShell(), "Date Error",e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void setTo() {
		try {
			if(selection.containsKey("to")) {
				selection.remove("to");
			}
			selection.put("to",df.parse("" + to.getYear() + "-" + (to.getMonth()+1) + "-" + to.getDay()));
			dteTo = new Date(df.parse("" + to.getYear() + "-" + (to.getMonth()+1) + "-" + to.getDay()).getTime());
			lblTo.setText("To: " + df.format(dteTo).trim());
		} catch (Exception e) {
			MessageDialog.openError(container.getShell(), "Date Error",e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void clear() {
		selection = new HashMap<String,Date>();
	}

	public Map<String,Date> getSelection() {
		return selection;
	}

	public void setSelection(Map<String,Date> selection) {
		firePropertyChange("selection", this.selection, this.selection = selection);
	}

	private boolean valid() {
		if(dteFrom==null&&dteTo==null) {
			selection = new HashMap<String,Date>();
			return true;
		}
		if(dteFrom!=null&&dteTo!=null) {
			if(dteFrom.compareTo(dteTo)>0) {
				MessageDialog.openError(getShell(), "Filter Error","Please select a <To:> date greater than '" + df.format(dteFrom) + "'.");
				return false;
			}
			selection = new HashMap<String,Date>();
			selection.put("from",dteFrom);
			selection.put("to",dteTo);
			return true;
		}
		if((dteFrom==null&&dteTo!=null)||(dteFrom!=null&&dteTo==null)) {
			MessageDialog.openError(getShell(), "Filter Error","Please select both dates or <Clear> to exit.");
			return false;
		}
		return false;
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

	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":) " + v);
	}

	//PROPERTY SUPPORT

	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private Button close;

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
	//PROPERTY SUPPORT

}

