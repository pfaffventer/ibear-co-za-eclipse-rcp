package za.co.ibear.swt.control.combo;

import java.beans.PropertyChangeListener;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;

import za.co.ibear.swt.control.table.ColumnTool;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;

public class BComboSingleDialog extends Dialog {

	private Composite container;
	private Text search;
	private TableViewer viewer;
	private Table table;
	private List<String> items = new ArrayList<String>();
	private String value = "";
	private int width = 100;
	private Composite relativeParent;

	public BComboSingleDialog(Shell parentShell,List<String> items,int width,Composite relativeParent) {
		super(parentShell);
		this.items = items;
		this.width = width;
		this.relativeParent = relativeParent;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		container = (Composite) super.createDialogArea(parent);

		parent.getShell().setText("Select");

		GridLayout gl_container = new GridLayout(1, false);
		gl_container.verticalSpacing = 3;
		gl_container.marginWidth = 3;
		gl_container.marginHeight = 3;
		gl_container.horizontalSpacing = 3;
		container.setLayout(gl_container);
		final BFilter rowFilter = new BFilter();
		
		close = new Button(container, SWT.NONE);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				close();
			}
		});
		close.setImage(ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/tick.png"));
		close.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		close.setText("Close");
		search = new Text(container, SWT.SEARCH | SWT.ICON_SEARCH | SWT.CANCEL | SWT.BORDER);
		search.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		search.setToolTipText("Enter filter text");
		search.setMessage("Search");
		search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				rowFilter.setSearchText(search.getText());
				viewer.refresh();
			}
		});
		viewer = new TableViewer(container, SWT.BORDER | SWT.VIRTUAL);
		viewer.setComparator(new BComparator());
		viewer.addFilter(rowFilter);
		table = viewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				setValue(table.getSelection()[0].getText());
				getShell().close();
			}
		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				setValue(table.getSelection()[0].getText());
				getShell().close();
			}
		}); 

		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		table.setLinesVisible(true);
		viewer.setContentProvider(new ArrayContentProvider());
		createColumn();

		container.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				table.getColumn(0).setWidth(container.getBounds().width-7);
			}
		});

		List<String> input = new ArrayList<String>();
		input.add("NONE");
		input.addAll(items);
		viewer.setInput(input);
		search.setFocus();

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

	private void createColumn() {
		ColumnTool Ctool = new ColumnTool(viewer);
		TableViewerColumn column = Ctool.createTableViewerColumn("Values",container.getBounds().width-7);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return (String) element;
			}
		});
	}

	public Table getDataTable() {
		return table;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		firePropertyChange("items", this.items, this.items = items);
	}

	public Table getData() {
		return table;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		firePropertyChange("value", this.value, this.value = value);
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

