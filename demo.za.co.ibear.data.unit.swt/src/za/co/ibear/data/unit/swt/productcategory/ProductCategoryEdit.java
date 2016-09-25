package za.co.ibear.data.unit.swt.productcategory;

import java.beans.PropertyChangeEvent;
import za.co.ibear.data.unit.swt.browse.UnitBrowseCombo;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import za.co.ibear.code.data.dictionary.definition.unit.ProductCategory;
import za.co.ibear.code.data.dictionary.system.edit.EditConstant;
import za.co.ibear.code.data.dictionary.system.edit.UnitEditor;
import za.co.ibear.code.data.dictionary.system.unit.Unit;
import za.co.ibear.data.unit.productcategory.ProductCategoryModel;
//import za.co.ibear.data.unit.swt.edit.EditCombo;
import za.co.ibear.data.unit.swt.edit.UnitEditCombo;
import za.co.ibear.swt.control.combo.BCombo;
import za.co.ibear.swt.control.combo.BComboConstant;
import za.co.ibear.swt.control.table.ColumnTool;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
public class ProductCategoryEdit extends Composite {
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private Image addIcon = ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/add.png");
	private Image deleteIcon = ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/cross.png");
	private Composite container;
	private CTabFolder folder;
	private CTabItem productcategoryTab;
	private Composite productcategoryComposite;
	private Text search;
	private TableViewer viewer;
	private Table table;
	private ProductCategory meta = new ProductCategory();
	private List<UnitEditor> editorData = null;
	private ProductCategoryModel editModel = null;
	private TableColumn editorColumn;
	private DataBindingContext ctx = null;
	private Map<String, BCombo> comboMap = new HashMap<String, BCombo>();
	int labelWidth = 200;
	public ProductCategoryEdit(Composite parent, int style, int labelWidth) throws Exception {
		super(parent, style);
		this.labelWidth = labelWidth;;
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 3;
		gridLayout.marginWidth = 3;
		gridLayout.marginHeight = 3;
		gridLayout.horizontalSpacing = 3;
		setLayout(gridLayout);
		container = new Composite(this, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		container.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				if (editorColumn != null) {
					editorWidth();
				}
			}
		});
		folder = new CTabFolder(container, SWT.BORDER | SWT.BOTTOM);
		folder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		productcategoryTab = new CTabItem(folder, SWT.NONE);
		productcategoryTab.setText("ProductCategory");
		productcategoryComposite = new Composite(folder, SWT.NONE);
		productcategoryTab.setControl(productcategoryComposite);
		GridLayout gl_productcategoryComposite = new GridLayout(3, false);
		gl_productcategoryComposite.verticalSpacing = 3;
		gl_productcategoryComposite.marginWidth = 0;
		gl_productcategoryComposite.marginHeight = 0;
		gl_productcategoryComposite.horizontalSpacing = 3;
		productcategoryComposite.setLayout(gl_productcategoryComposite);
		search = new Text(productcategoryComposite, SWT.SEARCH | SWT.ICON_SEARCH | SWT.CANCEL | SWT.BORDER);
		search.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		search.setToolTipText("Enter filter text");
		search.setMessage("Search");
		Button add = new Button(productcategoryComposite, SWT.NONE);
		if(meta.IS_READ_ONLY) {
			add.setEnabled(false);
		}
		add.setImage(addIcon);
		add.setToolTipText("Add record");
		add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				addRecord();
			}
		});
		Button delete = new Button(productcategoryComposite, SWT.NONE);
		if(meta.IS_READ_ONLY) {
			delete.setEnabled(false);
		}
		delete.setImage(deleteIcon);
		delete.setToolTipText("Delete record");
		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				deleteRecord();
			}
		});
		viewer = new TableViewer(productcategoryComposite, SWT.BORDER);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				if (!event.getSelection().isEmpty()) {
				}
			}
		});
		viewer.setContentProvider(new ArrayContentProvider());
		table = viewer.getTable();
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));
		createLabelColumn();
		table.pack();
		try {
			Method setItemHeightMethod = table.getClass().getDeclaredMethod("setItemHeight", int.class);
			setItemHeightMethod.setAccessible(true);
			setItemHeightMethod.invoke(table, 27);
		} catch (Exception e) {
		}
		editorData = new ArrayList<UnitEditor>(meta.EDITOR);
		viewer.setInput(editorData);
		createEditors(null);
		final ProductCategoryEditFilter rowFilter = new ProductCategoryEditFilter();
		viewer.addFilter(rowFilter);
		search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				destroyEditors();
				rowFilter.setSearchText(search.getText());
				viewer.refresh();
				try {
					createEditors(null);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		folder.setSelection(0);
		table.setFocus();
	}
	public void addRecord() {
		firePropertyChange("add-record", "STATIC", "NEW-RECORD");
	}
	public void deleteRecord() {
		firePropertyChange("delete-record", "STATIC", "DELETE-RECORD");
	}
	public Object createEditor(final UnitEditor editor) throws Exception {
		String propertyName = editor.getField().getName().substring(0, 1).toLowerCase() + editor.getField().getName().substring(1, editor.getField().getName().length());
		TableEditor tableEditor = new TableEditor(table);
		tableEditor.grabHorizontal = true;
		tableEditor.grabVertical = true;
		tableEditor.horizontalAlignment = SWT.FILL;
		tableEditor.verticalAlignment = SWT.FILL;
		switch (editor.getEditor()) {
		case EditConstant.TEXT:
			Text rt = new Text(table, SWT.BORDER);
			rt.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					firePropertyChange("do-edit", "STATIC", "TYPING-NOW");
				}
			});
			IObservableValue text_target = WidgetProperties.text(SWT.Modify).observe(rt);
			IObservableValue text_model = PojoProperties.value(propertyName).observe(editModel);
			ctx.bindValue(text_target, text_model);
			rt.setEditable(!editor.isReadOnly());
			return rt;
		case EditConstant.COMBO:
			Text rc = new Text(table, SWT.BORDER);
			rc.setEditable(!editor.isReadOnly());
			return rc;
		case EditConstant.CCOMBO:
			Text rcc = new Text(table, SWT.BORDER);
			rcc.setEditable(!editor.isReadOnly());
			return rcc;
		case EditConstant.DATE_TIME:
			Text rcd = new Text(table, SWT.BORDER);
			rcd.setEditable(!editor.isReadOnly());
			return rcd;
		case EditConstant.BCOMBO_SINGLE:
			BCombo bcombo_single = null;
			if (comboMap.containsKey(editor.getField().getName())) {
				bcombo_single = new BCombo(table, 370, editor.getField().getName(), editor.getField().getDescription(), comboMap.get(editor.getField().getName()).getItems(), BComboConstant.SINGLE);
			} else {
				bcombo_single = new BCombo(table, 370, editor.getField().getName(), editor.getField().getDescription(), getUnitComboData(editor.getUnit(),editor.getField().getName()), BComboConstant.SINGLE);
				comboMap.put(editor.getField().getName(), bcombo_single);
			}
			bcombo_single.addPropertyChangeListener("do-refresh", new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
					Method setProperty;
					try {
						setProperty = editModel.getClass().getDeclaredMethod("set" + editor.getField().getName(), String.class);
						setProperty.setAccessible(true);
						setProperty.invoke(editModel, ((Label) e.getNewValue()).getText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					firePropertyChange("do-edit", "STATIC", "TYPING-NOW");
				}
			});
			IObservableValue bcombo_single_target = WidgetProperties.text().observe(bcombo_single.getLabel());
			IObservableValue bcombo_single_model = PojoProperties.value(propertyName).observe(editModel);
			ctx.bindValue(bcombo_single_target, bcombo_single_model);
			return bcombo_single;
		case EditConstant.UNIT_EDIT:
			UnitEditCombo edit_combo = new UnitEditCombo(table,970,editor.getField().getDescription(),editor.getField().getName());
			edit_combo.addPropertyChangeListener("do-refresh", new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
					Method getProperty;
					Method setProperty;
					for(String columnName:editor.getReturnSet()) {
						try {
							getProperty = e.getNewValue().getClass().getDeclaredMethod("get" + columnName);
							getProperty.setAccessible(true);
							setProperty = editModel.getClass().getDeclaredMethod("set" + columnName, String.class);
							setProperty.setAccessible(true);
							setProperty.invoke(editModel, (String) getProperty.invoke(e.getNewValue()));
						} catch (Exception e1) {
							//FIXME
							e1.printStackTrace();
						}
					}
					firePropertyChange("do-edit", "STATIC", "UNIT-SELECTED");
				}
			});
			IObservableValue unit_edit_target = WidgetProperties.text().observe(edit_combo.getLabel());
			IObservableValue unit_edit_model = PojoProperties.value(propertyName).observe(editModel);
			ctx.bindValue(unit_edit_target, unit_edit_model);
			return edit_combo;
		case EditConstant.QUERY:
			UnitBrowseCombo categrory_query = new UnitBrowseCombo(table,1110,editor.getField().getDescription(),editor.getField().getName(),true,null,false);
			categrory_query.addPropertyChangeListener("do-refresh", new PropertyChangeListener() {
				public void propertyChange(final PropertyChangeEvent e) {
					Method getProperty;
					Method setProperty;
					for(String columnName:editor.getReturnSet()) {
						try {
							getProperty = e.getNewValue().getClass().getDeclaredMethod("get" + columnName);
							getProperty.setAccessible(true);
							setProperty = editModel.getClass().getDeclaredMethod("set" + columnName, String.class);
							setProperty.setAccessible(true);
							setProperty.invoke(editModel, (String) getProperty.invoke(e.getNewValue()));
						} catch (Exception e1) {
							//FIXME
							e1.printStackTrace();
						}
					}
					firePropertyChange("do-edit", "STATIC", "UNIT-SELECTED");
				}
			});
			String p_name = (String) editor.getReturnSet().toArray()[0];
			propertyName = p_name.substring(0, 1).toLowerCase() + p_name.substring(1, p_name.length());
			
			IObservableValue unit_query_target = WidgetProperties.text().observe(categrory_query.getLabel());
			IObservableValue unit_query_model = PojoProperties.value(propertyName).observe(editModel);
			ctx.bindValue(unit_query_target, unit_query_model);
			
			return	categrory_query;
		default:
			break;
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String> getUnitComboData(Unit unit, String columnName) throws Exception {
		List<String> c_data = null;
		String name = "za.co.ibear.data.unit." + unit.NAME.toLowerCase() + "." + unit.NAME.trim() + "Link";
		Class unitLink = Class.forName(name);
		java.lang.reflect.Constructor constructor = unitLink.getConstructor();
		Object invoker = constructor.newInstance();
		java.lang.reflect.Method methodPop = unitLink.getMethod("pop");
		java.lang.reflect.Method methodToDistinctColumnArray = unitLink.getMethod("toDistinctColumnArray", String.class);
		java.lang.reflect.Method methodDisconnect = unitLink.getMethod("disconnect");
		methodPop.invoke(invoker);
		c_data = (List<String>) methodToDistinctColumnArray.invoke(invoker, columnName);
		methodDisconnect.invoke(invoker);
		return c_data;
	}
	public void destroyEditors() {
		table.setRedraw(false);
		if (editorColumn != null) {
			editorColumn.dispose();
		}
		for (Control c : table.getChildren()) {
			c.dispose();
		}
	}
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":) " + v);
	}
	public void createEditors(ProductCategoryModel data) throws Exception {
		if (data != null) {
			this.editModel = data;
		} else {
			this.ctx = new DataBindingContext();
		}
		editorColumn = new TableColumn(table, SWT.NONE);
		editorWidth();
		TableEditor tableEditor;
		int row = 0;
		for (TableItem i : table.getItems()) {
			// FIXME all types of editors e.g. BCombo etc.
			UnitEditor editor = getEditorFromDescription(i.getText().substring(0, i.getText().length() - 2));
			tableEditor = new TableEditor(table);
			tableEditor.grabHorizontal = true;
			tableEditor.grabVertical = true;
			tableEditor.horizontalAlignment = SWT.FILL;
			tableEditor.verticalAlignment = SWT.FILL;
			switch (editor.getEditor()) {
			case EditConstant.TEXT:
				final Text text = (Text) createEditor(editor);
				tableEditor.setEditor(text, table.getItem(row), 1);
				FocusListener focusListener = new FocusListener() {
					@Override
					public void focusGained(FocusEvent e) {
						text.setSelection(0);
					}
					@Override
					public void focusLost(FocusEvent e) {
					}
				};
				tableEditor.getEditor().addFocusListener(focusListener);
				break;
			case EditConstant.COMBO:
			case EditConstant.CCOMBO:
			case EditConstant.DATE_TIME:
			case EditConstant.BCOMBO_SINGLE:
				BCombo bcombo_single = (BCombo) createEditor(editor);
				tableEditor.setEditor(bcombo_single, table.getItem(row), 1);
				break;
			case EditConstant.UNIT_EDIT:
				BCombo edit_combo = (BCombo) createEditor(editor);
				tableEditor.setEditor(edit_combo, table.getItem(row), 1);
				break;
			case EditConstant.QUERY:
				BCombo query_combo = (BCombo) createEditor(editor);
				tableEditor.setEditor(query_combo, table.getItem(row), 1);
				break;
			default:
				break;
			}
			row++;
		}
		table.setRedraw(true);
	}
	private UnitEditor getEditorFromDescription(String description) {
		for (UnitEditor editor : meta.EDITOR) {
			if (editor.getField().getDescription().equals(description)) {
				return editor;
			}
		}
		return null;
	}
	private void editorWidth() {
		// TODO pass label column width then ->
		// editorColumn.setWidth(container.getBounds().width-lColumnWidth-10);
		editorColumn.setWidth(container.getBounds().width - 130);
	}
	private void createLabelColumn() {
		ColumnTool Ctool = new ColumnTool(viewer);
		TableViewerColumn column = Ctool.createTableViewerColumn("Labels", labelWidth);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public Font getFont(Object element) {
				return SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD);
			}
			@Override
			public Color getForeground(Object element) {
				return SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER);
			}
			@Override
			public String getText(Object element) {
				return ((UnitEditor) element).getField().getDescription() + " :";
			}
		});
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	public List<UnitEditor> getEditData() {
		return editorData;
	}
	public void setEditData(List<UnitEditor> editData) {
		firePropertyChange("editData", this.editorData, this.editorData = editData);
	}
	public ProductCategoryModel getEditModel() {
		return editModel;
	}
	public void setEditModel(ProductCategoryModel editModel) {
		firePropertyChange("editModel", this.editModel, this.editModel = editModel);
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
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	protected void firePropertyChange(String propertyName, Object value) {
		changeSupport.firePropertyChange(propertyName, value, null);
	}
}
class ProductCategoryEditFilter extends ViewerFilter {
	private String searchString;
	public void setSearchText(String s) {
		this.searchString = ".*" + s + ".*";
	}
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		UnitEditor entry = (UnitEditor) element;
		if (entry.getField().getName().toLowerCase().matches(searchString.toLowerCase())) {
			return true;
		}
		return false;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}
}
class ProductCategoryEditComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;
	public ProductCategoryEditComparator() {
		this.propertyIndex = 0;
		direction = DESCENDING;
	}
	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}
	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			direction = 1 - direction;
		} else {
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		UnitEditor entry01 = (UnitEditor) e1;
		UnitEditor entry02 = (UnitEditor) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = entry01.getField().getName().compareTo(entry02.getField().getName());
			break;
		default:
			rc = 0;
		}
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":) " + v);
	}
}
