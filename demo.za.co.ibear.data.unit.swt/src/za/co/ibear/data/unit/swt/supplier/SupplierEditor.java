package za.co.ibear.data.unit.swt.supplier;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.lang.reflect.Method;
import java.util.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import za.co.ibear.data.unit.supplier.SupplierLink;
import za.co.ibear.data.unit.supplier.SupplierModel;
import za.co.ibear.data.unit.supplier.SupplierTransaction;
import za.co.ibear.data.unit.swt.browse.UnitBrowseDialog;
import za.co.ibear.code.data.dictionary.definition.unit.Supplier;
import za.co.ibear.data.unit.swt.browse.UnitBrowseCombo;
import za.co.ibear.swt.control.combo.BCombo;
import za.co.ibear.swt.control.combo.BComboConstant;
import za.co.ibear.data.unit.swt.edit.UnitEditComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.ResourceManager;
public class SupplierEditor extends UnitEditComposite implements IProgressMonitor {
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private Composite parent;
	private Composite composite;
	private SashForm sash;
	private Composite dataComposite;
	private TableViewer dataViewer;
	private Table dataTable;
	private SupplierModel selected;
	private Composite filterComposite;
	private TableViewer filterViewer;
	private Table filterTable;
	private Composite editorComposite;
	private ProgressBar progress;
	private SupplierLink data = new SupplierLink();
	final Set<BCombo> multiSet = new LinkedHashSet<BCombo>();
	final Set<BCombo> unitSet = new LinkedHashSet<BCombo>();
	final Set<BCombo> querySet = new LinkedHashSet<BCombo>();
	final Set<BCombo> dateSet = new LinkedHashSet<BCombo>();
	private SupplierEdit supplierEdit;
	private Supplier meta = new Supplier();
	protected boolean SHELL_PARENT = false;
	private Set<SupplierTransaction> connectionSet = new HashSet<SupplierTransaction>();
	private IStructuredSelection selection;
	private Image clearIcon = ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/context-clear.gif");
	private int headerSequence;
	@SuppressWarnings({ "rawtypes" })
	private List addSelection;
	private Text search;
	private SupplierComparator comparator = null;
	private String queryColumn = null;
	private List<String> queryItems = null;
	
	public SupplierEditor(final Composite parent, int style, int[] editorWeights,int labelWidth) throws Exception  {
		super(parent, style);
		this.parent = parent;
		if(parent.getParent().getParent().getClass().getSimpleName().equals("Shell")) {
			SHELL_PARENT = true;
		}
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if(connectionSet.size()>0) {
					try {
						connectionSet.iterator().next().commit();
					} catch (Exception e1) {
						MessageDialog.openError(parent.getShell(), "Filter Error",e1.getMessage());
						e1.printStackTrace();
					}
					connectionSet.iterator().next().disconnect();
					connectionSet.remove(connectionSet.iterator().next());
				}
				data.disconnect();
			}
		});
		GridLayout gl_this = new GridLayout(1, false);
		gl_this.marginRight = 3;
		gl_this.marginBottom = 3;
		gl_this.marginLeft = 3;
		gl_this.verticalSpacing = 0;
		gl_this.marginWidth = 0;
		gl_this.marginHeight = 0;
		gl_this.horizontalSpacing = 0;
		setLayout(gl_this);
		composite = new Composite(this, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sash = new SashForm(composite, SWT.NONE);
		data = new SupplierLink();
		if(!meta.IS_ELEMENT) {
			data.toSupplierModelList(null);
		}
		filterComposite = new Composite(sash, SWT.NONE);
		GridLayout gl_filterBottom = new GridLayout(1, false);
		gl_filterBottom.marginTop = 3;
		gl_filterBottom.marginHeight = 0;
		gl_filterBottom.verticalSpacing = 0;
		gl_filterBottom.marginWidth = 0;
		gl_filterBottom.horizontalSpacing = 0;
		filterComposite.setLayout(gl_filterBottom);
		filterViewer = new TableViewer(filterComposite, SWT.FULL_SELECTION);
		filterTable = filterViewer.getTable();
		filterTable.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		filterTable.setLinesVisible(true);
		filterTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		final TableColumn column = new TableColumn(filterTable, SWT.NONE);
		filterComposite.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				column.setWidth(filterComposite.getBounds().width-5);
			}
		});
		TableEditor editor;
		if(SHELL_PARENT) {
			TableItem itemExit = new TableItem(filterTable, SWT.NONE);
			editor = new TableEditor(filterTable);
			editor.grabHorizontal = true;
			editor.horizontalAlignment = SWT.FILL;
			Button exit = new Button(filterTable, SWT.PUSH);
			exit.setText(" Exit ");
			exit.setToolTipText("Exit window without selection");
			exit.setImage(ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/cross.png"));
			editor.setEditor(exit,itemExit,0);
			exit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					firePropertyChange("exit", "OPEN","EXIT");
				}
			});
			TableItem itemClose = new TableItem(filterTable, SWT.NONE);
			editor = new TableEditor(filterTable);
			editor.grabHorizontal = true;
			editor.horizontalAlignment = SWT.FILL;
			Button close = new Button(filterTable, SWT.PUSH);
			close.setText(" Close ");
			close.setToolTipText("Close window");
			close.setImage(ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/tick.png"));
			editor.setEditor(close,itemClose,0);
			close.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					firePropertyChange("close", "OPEN","CLOSE");
				}
			});
		}
		TableItem itemReset = new TableItem(filterTable, SWT.NONE);
		editor = new TableEditor(filterTable);
		editor.grabHorizontal = true;
		editor.horizontalAlignment = SWT.FILL;
		Button reset = new Button(filterTable, SWT.PUSH);
		reset.setText(" Reset ");
		reset.setToolTipText("Reset all filters");
		reset.setImage(clearIcon);
		editor.setEditor(reset,itemReset,0);
		reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				queryItems = new ArrayList<String>();
				reset();
			}
		});
		TableItem itemSearch = new TableItem(filterTable, SWT.NONE);
		editor = new TableEditor(filterTable);
		editor.grabHorizontal = true;
		editor.horizontalAlignment = SWT.FILL;
		search = new Text(filterTable, SWT.SEARCH | SWT.ICON_SEARCH | SWT.CANCEL | SWT.BORDER);
		search.setToolTipText("Type filter text and press <Enter>");
		search.setMessage("Search");
		search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==SWT.CR||e.keyCode==SWT.KEYPAD_CR) {
					search(search.getText());
				}
			}
		});
		editor.setEditor(search,itemSearch,0);
		TableItem filterItem = null;
		BCombo filter = null;
		for(String bcombo_meta:meta.BCOMBO) {
			String bcombo = bcombo_meta.split(">>")[0];
			int type = Integer.valueOf(bcombo_meta.split(">>")[1]);
			if(type==BComboConstant.MULTI) {
				filterItem = new TableItem(filterTable, SWT.NONE);
				editor = new TableEditor(filterTable);
				editor.grabHorizontal = true;
				editor.horizontalAlignment = SWT.FILL;
				filter = new BCombo(filterTable,370,meta.getField(bcombo).getDescription(),meta.getField(bcombo).getName(),new ArrayList<String>(data.toDistinctColumnArray(bcombo)),BComboConstant.MULTI);
				filter.addPropertyChangeListener("close",new PropertyChangeListener() {
					public void propertyChange(final PropertyChangeEvent e) {
						select();
					}
				});
				editor.setEditor(filter,filterItem,0);
				multiSet.add(filter);
			}
			if(type==BComboConstant.UNIT_BROWSE) {
				filterItem = new TableItem(filterTable, SWT.NONE);
				editor = new TableEditor(filterTable);
				editor.grabHorizontal = true;
				editor.horizontalAlignment = SWT.FILL;
				filter = new UnitBrowseCombo(filterTable,1110,meta.getField(bcombo).getDescription(),meta.getField(bcombo).getName(),false,data.toDistinctColumnArray(meta.getField(bcombo).getName()),false);
				filter.addPropertyChangeListener("select",new PropertyChangeListener() {
					public void propertyChange(final PropertyChangeEvent e) {
						select();
					}
				});
				editor.setEditor(filter,filterItem,0);
				unitSet.add(filter);
			}
			if(type==BComboConstant.DATE) {
				filterItem = new TableItem(filterTable, SWT.NONE);
				editor = new TableEditor(filterTable);
				editor.grabHorizontal = true;
				editor.horizontalAlignment = SWT.FILL;
				filter = new BCombo(filterTable, 370,meta.getField(bcombo).getDescription(),meta.getField(bcombo).getName(),null,BComboConstant.DATE);
				filter.addPropertyChangeListener("close",new PropertyChangeListener() {
					public void propertyChange(final PropertyChangeEvent e) {
						select();
					}
				});
				editor.setEditor(filter,filterItem,0);
				dateSet.add(filter);
			}			
		}
		data.setMultiSet(multiSet);
		data.setUnitSet(unitSet);
		data.setQuerySet(querySet);
		data.setDateSet(dateSet);
		for (int i = 0; i < 1; i++) {
			filterTable.getColumn(i).pack();
		}
		filterTable.pack();
		try {
			Method setItemHeightMethod = filterTable.getClass().getDeclaredMethod("setItemHeight", int.class);
			setItemHeightMethod.setAccessible(true);
			setItemHeightMethod.invoke(filterTable, 27);
		} catch (Exception e) {
		}
		dataComposite = new Composite(sash, SWT.NONE);
		GridLayout gl_table = new GridLayout(1, false);
		gl_table.marginBottom = 3;
		gl_table.marginTop = 3;
		gl_table.verticalSpacing = 0;
		gl_table.marginWidth = 0;
		gl_table.marginHeight = 0;
		gl_table.horizontalSpacing = 0;
		dataComposite.setLayout(gl_table);
		dataViewer = new TableViewer(dataComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI  | SWT.VIRTUAL);
		setTable(dataViewer.getTable());
		dataViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				selection = (IStructuredSelection)	dataViewer.getSelection();
				setSelected((SupplierModel) selection.getFirstElement());
			}
		});
		dataTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==127) {
					deleteRecord();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		dataTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				firePropertyChange("close", "OPEN","CLOSE");
			}
		});
		dataTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		dataViewer.setContentProvider(contentProvider);
		dataViewer.setLabelProvider(new SupplierLabelProvider());
		new TableColumn(dataTable, SWT.LEFT).setText("Supplier");
		new TableColumn(dataTable, SWT.LEFT).setText("Name");
		new TableColumn(dataTable, SWT.LEFT).setText("Address1");
		new TableColumn(dataTable, SWT.LEFT).setText("Address2");
		new TableColumn(dataTable, SWT.LEFT).setText("Address3");
		new TableColumn(dataTable, SWT.LEFT).setText("Address4");
		new TableColumn(dataTable, SWT.LEFT).setText("Address5");
		new TableColumn(dataTable, SWT.LEFT).setText("User");
		new TableColumn(dataTable, SWT.RIGHT).setText("Time Created");
		
		for (int i = 0, n = dataTable.getColumnCount(); i < n; i++) {
			dataTable.getColumn(i).pack();
		}
		comparator = new SupplierComparator();
		dataTable.getColumn(0).addSelectionListener(getSelectionAdapter(dataTable.getColumn(0), 0));
		dataTable.getColumn(1).addSelectionListener(getSelectionAdapter(dataTable.getColumn(1), 1));
		dataTable.getColumn(2).addSelectionListener(getSelectionAdapter(dataTable.getColumn(2), 2));
		dataTable.getColumn(3).addSelectionListener(getSelectionAdapter(dataTable.getColumn(3), 3));
		dataTable.getColumn(4).addSelectionListener(getSelectionAdapter(dataTable.getColumn(4), 4));
		dataTable.getColumn(5).addSelectionListener(getSelectionAdapter(dataTable.getColumn(5), 5));
		dataTable.getColumn(6).addSelectionListener(getSelectionAdapter(dataTable.getColumn(6), 6));
		dataTable.getColumn(7).addSelectionListener(getSelectionAdapter(dataTable.getColumn(7), 7));
		dataTable.getColumn(8).addSelectionListener(getSelectionAdapter(dataTable.getColumn(8), 8));
		dataTable.getColumn(0).setWidth(120);
		dataTable.getColumn(1).setWidth(270);
		dataTable.getColumn(2).setWidth(210);
		dataTable.getColumn(3).setWidth(210);
		dataTable.getColumn(4).setWidth(210);
		dataTable.getColumn(5).setWidth(210);
		dataTable.getColumn(6).setWidth(210);
		dataTable.getColumn(7).setWidth(70);
		dataTable.getColumn(8).setWidth(210);
		dataTable.setLinesVisible(true);
		dataTable.setHeaderVisible(true);
		dataViewer.setInput(new WritableList(data.getData(), SupplierModel.class));
		editorComposite = new Composite(sash, SWT.NONE);
		editorComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		supplierEdit = new SupplierEdit(editorComposite, SWT.NONE, labelWidth);
		supplierEdit.addPropertyChangeListener("do-edit",new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent e) {
				dataTable.setRedraw(false);
				dataTable.setSelection(dataTable.getSelectionIndex());
				if(e.getNewValue().toString().equals("UNIT-SELECTED")) {
					setSelected((SupplierModel) dataTable.getSelection()[0].getData());
				}
				dataTable.setRedraw(true);
				dataViewer.refresh();
			}
		});
		supplierEdit.addPropertyChangeListener("add-record",new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent e) {
				addRecord();
			}
		});
		supplierEdit.addPropertyChangeListener("delete-record",new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent e) {
				deleteRecord();
			}
		});
		GridLayout gridLayout = (GridLayout) supplierEdit.getLayout();
		gridLayout.marginTop = 3;
		gridLayout.marginLeft = 5;
		gridLayout.marginWidth = 0;
		gridLayout.marginBottom = 3;
		gridLayout.marginHeight = 0;
		sash.setWeights(editorWeights);
		progress = new ProgressBar(this, SWT.NONE);
		GridData gd_progress = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_progress.heightHint = 12;
		progress.setLayoutData(gd_progress);
		if(!meta.IS_READ_ONLY) {
			MenuManager popupMenu = new MenuManager();
			IAction resetAction = new ResetAction();
			popupMenu.add(resetAction);
			IAction addAction = new AddRecordAction();
			popupMenu.add(addAction);
			IAction deleteAction = new DeleteRecordAction();
			popupMenu.add(deleteAction);
			Menu menu = popupMenu.createContextMenu(dataTable);
			dataTable.setMenu(menu);
		}
	}
	private SelectionAdapter getSelectionAdapter(final TableColumn column,final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(dataViewer.getComparator()==null) {
					dataViewer.setComparator(comparator);
				}
				comparator.setColumn(index);
				int dir = comparator.getDirection();
				dataViewer.getTable().setSortDirection(dir);
				dataViewer.getTable().setSortColumn(column);
				dataViewer.refresh();
			}
		};
		return selectionAdapter;
	}
	private void search(final String text) {
		Job job = new Job("JOB_SEARCH") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask("Executing search.",5);
				monitor.worked(2);
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						data.stringSearch(text);
						monitor.worked(3);
						dataViewer.setInput(new WritableList(data.getFilterData(), SupplierModel.class));
						monitor.worked(4);
					}
				});
				monitor.worked(5);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		IJobManager manager = Job.getJobManager();
		final IProgressMonitor progress = (IProgressMonitor) this;
		ProgressProvider provider = new ProgressProvider() {
			@Override
			public IProgressMonitor createMonitor(Job job) {
				return progress;
			}
		};
		manager.setProgressProvider(provider);
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
//						prime();
					}
				});
				if (!event.getResult().isOK()) {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							MessageDialog.openError(parent.getShell(), "Filter Error", "Fatal error could not do selection.");
						}
					});
				} else {
					for(BCombo bcombo:unitSet) {
						UnitBrowseCombo ub = ((UnitBrowseCombo) bcombo);
						ub.getBrowseDialog().setSelection(ub.getBrowseSelection());
					}
				}
			}
		});
		job.schedule(); 	
	}
	public void selectDetail(int headerSequence) {
		this.headerSequence = headerSequence;
		try {
			data.toSupplierModelList(" WHERE UnitSequence = " + headerSequence);
		} catch (Exception e) {
		e.printStackTrace();
		}
		dataViewer.setInput(new WritableList(data.getData(), SupplierModel.class));
		reset();
	}
	private class AddRecordAction extends Action {
		public AddRecordAction() {
			super("Add record");
		}
		public void run() {
			addRecord();
		}
	}
	private class ResetAction extends Action {
		public ResetAction() {
			super("Reset");
		}
		public void run() {
			reset();
		}
	}
	private class DeleteRecordAction extends Action {
		public DeleteRecordAction() {
			super("Delete record");
		}
		public void run() {
			deleteRecord();
		}
	}
	private void reset() {
		dataViewer.setComparator(null);
		dataViewer.getTable().setSortColumn(null);
		dataViewer.refresh();
		Job job = new Job("JOB_CLEAR") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask("Executing filter.",5);
				monitor.worked(2);
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						monitor.worked(3);
						try {
							data.doReset();
						} catch (Exception e) {
							MessageDialog.openError(parent.getShell(), "Filter Error",e.getMessage());
							e.printStackTrace();
						}
						monitor.worked(4);
						dataViewer.setInput(new WritableList(data.getFilterData(), SupplierModel.class));
					}
				});
				monitor.worked(5);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		IJobManager manager = Job.getJobManager();
		final IProgressMonitor progress = (IProgressMonitor) this;
		ProgressProvider provider = new ProgressProvider() {
			@Override
			public IProgressMonitor createMonitor(Job job) {
				return progress;
			}
		};
		manager.setProgressProvider(provider);
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				if (!event.getResult().isOK()) {
					MessageDialog.openError(parent.getShell(), "Filter Error", "Fatal error could not clear selection.");
				} else {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							search.setText("");
						}
					});
					for(BCombo bcombo:unitSet) {
						UnitBrowseCombo ub = ((UnitBrowseCombo) bcombo);
						ub.getBrowseDialog().setSelection(ub.getBrowseSelection());
					}
				}
			}
		});
		job.schedule();
	}
	private void select() {
		Job job = new Job("JOB_SELECT") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask("Executing filter.",5);
				monitor.worked(2);
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						data.doSelect();
						monitor.worked(3);
						dataViewer.setInput(new WritableList(data.getFilterData(), SupplierModel.class));
						monitor.worked(4);
					}
				});
				monitor.worked(5);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		IJobManager manager = Job.getJobManager();
		final IProgressMonitor progress = (IProgressMonitor) this;
		ProgressProvider provider = new ProgressProvider() {
			@Override
			public IProgressMonitor createMonitor(Job job) {
				return progress;
			}
		};
		manager.setProgressProvider(provider);
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
//						prime();
					}
				});
				if (!event.getResult().isOK()) {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							MessageDialog.openError(parent.getShell(), "Filter Error", "Fatal error could not do selection.");
						}
					});
				} else {
					for(BCombo bcombo:unitSet) {
						UnitBrowseCombo ub = ((UnitBrowseCombo) bcombo);
						ub.getBrowseDialog().setSelection(ub.getBrowseSelection());
					}
				}
			}
		});
		job.schedule(); 	
	}
	public List<String> getQueryItems() {
		return queryItems;
	}
	public void setQueryItems(List<String> queryItems) {
		this.queryItems = queryItems;
	}
	public String getQueryColumn() {
		return queryColumn;
	}
	public void setQueryColumn(String queryColumn) {
		this.queryColumn = queryColumn;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}
	public Table getTable() {
		return dataTable;
	}
	public void setTable(Table table) {
		this.dataTable = table;
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	@Override
	public void beginTask(final String name, final int totalWork) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				setTaskName(name); 
				progress.setMaximum(totalWork);
				progress.setToolTipText(name);
			}
		});
	}
	@Override
	public void worked(final int work) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				progress.setSelection(progress.getSelection() + work);
			}
		});
	}
	@Override
	public void done() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				progress.setMaximum(0);
				progress.setSelection(0);
				setTaskName("");
				progress.setToolTipText("");
			}
		});
	}
	@Override
	public void internalWorked(double work) {
	}
	@Override
	public boolean isCanceled() {
		return false;
	}
	@Override
	public void setCanceled(boolean value) {
	}
	@Override
	public void setTaskName(String name) {
	}
	@Override
	public void subTask(String name) {
	}
	public Table getDataTable() {
		return dataTable;
	}
	public void setDataTable(Table dataTable) {
		this.dataTable = dataTable;
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
	public SupplierModel getSelected() {
		return selected;
	}
	private void deleteRecord() {
		if(selection==null) {
			MessageDialog.openError(parent.getShell(), "Edit Error","Please select a record to delete.");
			return;
		}
		boolean deleteRecord = MessageDialog.openQuestion(parent.getShell(),"Delete Record","Delete the selected record(s)?");
		if(!deleteRecord) {
			return;
		}
		for(Object object:selection.toArray()) {
			SupplierModel supplierModel = (SupplierModel) object;
			if(connectionSet.size()==0) {
				try {
					connectionSet.add(new SupplierTransaction(supplierModel));
					connectionSet.iterator().next().begin();
				} catch (Exception e) {
					MessageDialog.openError(parent.getShell(), "Edit Error",e.getMessage());
					return;
				}
			}
			try {
				connectionSet.iterator().next().delete();
			} catch (Exception e1) {
				MessageDialog.openError(parent.getShell(), "Edit Error",e1.getMessage());
				return;
			}
			boolean filtered = false;
			if(data.getFilterData().size()!=0) {
				if(data.getFilterData().size()!=data.getData().size()) {
					filtered = true;
				}
			}
			if(selection.size()>0) {
				try {
					data.doDelete((SupplierModel) selection.getFirstElement(), filtered);
				} catch (Exception e) {
					MessageDialog.openError(parent.getShell(), "Edit Error",e.getMessage());
					return;
				}
			}
			if(filtered) {
				dataViewer.setInput(new WritableList(data.getFilterData(), SupplierModel.class));
			} else {
				dataViewer.setInput(new WritableList(data.getData(), SupplierModel.class));
			}
			if(selection.size()==0) {
				if(dataTable.getItemCount()>0) {
					dataTable.setSelection(dataViewer.getTable().getItemCount()-1);
					setSelected((SupplierModel) dataTable.getSelection()[0].getData());
				} else {
					supplierEdit.destroyEditors();
					try {
						supplierEdit.createEditors(new SupplierModel(true));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else  {
				if(dataTable.getItemCount()>0) {
					dataTable.setSelection(dataTable.getSelectionIndices()[0]);
					setSelected((SupplierModel) dataTable.getSelection()[0].getData());
				} else {
					supplierEdit.destroyEditors();
					try {
						supplierEdit.createEditors(new SupplierModel(true));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	private void addRecord() {
		try {
			if(dataTable.getItemCount()>0) {
				if(dataTable.getSelectionIndices().length>0) {
					dataTable.setSelection(dataTable.getSelectionIndices()[0]);
					setSelected((SupplierModel) dataTable.getSelection()[0].getData());
				}
			}
			if(meta.IS_ELEMENT) {
				UnitBrowseDialog browseDialog = new UnitBrowseDialog(parent.getShell(),1110,editorComposite,meta.ELEMENT_SELECTOR.getUnit().NAME,false,null);
				browseDialog.addPropertyChangeListener("selection_changed",new PropertyChangeListener() {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					public void propertyChange(final PropertyChangeEvent e) {
						if(e.getNewValue()!=null) {
							if(e.getNewValue()!=null) {
								addSelection = new ArrayList();
								List<TableItem> selection = ((List<TableItem>) e.getNewValue());
								for(TableItem item:selection) {
									addSelection.add(item.getData());
								}
							}
						}
					}
				});
				browseDialog.open();
				for(Object selected:addSelection) {
					SupplierModel newSupplier = new SupplierModel();
					newSupplier.setUnitSequence(getHeaderSequence());
			int next = data.nextUnitSequence();
			newSupplier.setUnitSequence(next);
					Method getProperty;
					Method setProperty;
					for(String columnName:meta.ELEMENT_SELECTOR.getReturnSet()) {
						getProperty = selected.getClass().getDeclaredMethod("get" + columnName);
						getProperty.setAccessible(true);
						setProperty = newSupplier.getClass().getDeclaredMethod("set" + columnName,String.class);
						setProperty.setAccessible(true);
						setProperty.invoke(newSupplier, getProperty.invoke(selected));
					}
					if(connectionSet.size()==0) {
						connectionSet.add(new SupplierTransaction(newSupplier));
						connectionSet.iterator().next().begin();
					}
					connectionSet.iterator().next().insert(newSupplier);
					boolean filtered = false;
					if(data.getFilterData().size()!=0) {
						if(data.getFilterData().size()!=data.getData().size()) {
							filtered = true;
						}
					}
					data.getData().add(newSupplier);
					dataViewer.setInput(null);
					if(filtered) {
						data.getFilterData().add(newSupplier);
						dataViewer.setInput(new WritableList(data.getFilterData(), SupplierModel.class));
					} else {
						dataViewer.setInput(new WritableList(data.getData(), SupplierModel.class));
					}
					dataTable.setSelection(dataTable.getItemCount()-1);
					setSelected((SupplierModel) dataTable.getSelection()[0].getData());
				}
			} else {
				SupplierModel newSupplier = new SupplierModel();
				newSupplier.setUnitSequence(getHeaderSequence());
			int next = data.nextUnitSequence();
			newSupplier.setUnitSequence(next);
				if(connectionSet.size()==0) {
					connectionSet.add(new SupplierTransaction(newSupplier));
					connectionSet.iterator().next().begin();
				}
				connectionSet.iterator().next().insert(newSupplier);
				boolean filtered = false;
				if(data.getFilterData().size()!=0) {
					if(data.getFilterData().size()!=data.getData().size()) {
						filtered = true;
					}
				}
				data.getData().add(newSupplier);
				dataViewer.setInput(null);
				if(filtered) {
					data.getFilterData().add(newSupplier);
					dataViewer.setInput(new WritableList(data.getFilterData(), SupplierModel.class));
				} else {
					dataViewer.setInput(new WritableList(data.getData(), SupplierModel.class));
				}
				dataTable.setSelection(dataTable.getItemCount()-1);
				setSelected((SupplierModel) dataTable.getSelection()[0].getData());
			}
		} catch (Exception e) {
			MessageDialog.openError(parent.getShell(), "Insert Error",e.getMessage());
			e.printStackTrace();
		}
	}
	public void refresh() throws Exception {
		data.pop();
		boolean filtered = false;
		if(data.getFilterData().size()!=0) {
			if(data.getFilterData().size()!=data.getData().size()) {
				filtered = true;
			}
		}
		dataViewer.setInput(null);
		if(filtered) {
			dataViewer.setInput(new WritableList(data.getFilterData(), SupplierModel.class));
		} else {
			dataViewer.setInput(new WritableList(data.getData(), SupplierModel.class));
		}
	}
	
	public void setSelected(SupplierModel selected) {
		boolean error = false;
		if(selected==null) {
			return;
		}
		try {
			switchTransaction(selected);
		} catch (Exception e) {
			error = true;
			dataTable.setSelection(-1);
		}
		supplierEdit.destroyEditors();
		try {
			if(!error) {
				supplierEdit.createEditors(selected);
			}
		} catch (Exception e) {
			MessageDialog.openError(parent.getShell(), "Select Error",e.getMessage());
			e.printStackTrace();
			return;
		}
		firePropertyChange("selected", null, this.selected = selected);
	}
	public void switchTransaction(SupplierModel unit) throws Exception {
		if(connectionSet.size()>0) {
			connectionSet.iterator().next().commit();
			connectionSet.iterator().next().disconnect();
			connectionSet.remove(connectionSet.iterator().next());
		}
		connectionSet.add(new SupplierTransaction(unit));
		try {
			connectionSet.iterator().next().begin();
		} catch (Exception e) {
			connectionSet.iterator().next().disconnect();
			connectionSet.remove(connectionSet.iterator().next());
			MessageDialog.openError(parent.getShell(), "Select Error",e.getMessage());
			throw e;
		}
	}
	public int getHeaderSequence() {
		return headerSequence;
	}
	public void setHeaderSequence(int headerSequence) {
		this.headerSequence = headerSequence;
	}
}
