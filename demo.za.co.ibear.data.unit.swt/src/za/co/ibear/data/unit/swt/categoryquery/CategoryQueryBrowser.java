package za.co.ibear.data.unit.swt.categoryquery;

import java.beans.PropertyChangeEvent;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyAdapter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
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
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import za.co.ibear.code.data.dictionary.definition.query.CategoryQuery;
import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.primary.key.PrimaryKey;
import za.co.ibear.data.unit.categoryquery.CategoryQueryLink;
import za.co.ibear.data.unit.categoryquery.CategoryQueryModel;
import za.co.ibear.data.unit.swt.browse.UnitBrowseCombo;
import za.co.ibear.data.unit.swt.browse.UnitBrowseComposite;
import za.co.ibear.data.unit.swt.browse.UnitBrowseDialogState;
import za.co.ibear.swt.control.combo.BCombo;
import za.co.ibear.swt.control.combo.BComboConstant;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import gk.jfilter.JFilter;

import org.eclipse.wb.swt.ResourceManager;

public class CategoryQueryBrowser extends UnitBrowseComposite implements IProgressMonitor {
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private Composite parent;
	private Composite composite;
	private SashForm sash;
	private Composite dataComposite;
	private TableViewer dataViewer;
	private Table dataTable;
	private CategoryQueryModel selected;
	private Composite filterComposite;
	private TableViewer filterViewer;
	private Table filterTable;
	private ProgressBar progress;
	private CategoryQueryLink data = new CategoryQueryLink();
	final Set<BCombo> multiSet = new LinkedHashSet<BCombo>();
	final Set<BCombo> unitBrowseSet = new LinkedHashSet<BCombo>();
	final Set<BCombo> dateSet = new LinkedHashSet<BCombo>();
	private CategoryQuery meta = new CategoryQuery();
	protected boolean SHELL_PARENT = false;
	private Image clearIcon = ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/context-clear.gif");
	private TableItem currentItem = null;
	private boolean FIRST_FOCUS = true;
	private boolean SINGLE_SELECT = false;
	private int itemCountBefore = 0;
	private UnitBrowseDialogState savedState = null;
	private Text search;
	private List<String> initFilter = new ArrayList<String>();
	private CategoryQueryComparator comparator = null;
	@SuppressWarnings("unchecked")
	public CategoryQueryBrowser(final Composite parent, int style,UnitBrowseDialogState state,Map<String,UnitBrowseDialogState> unitBorwseStateMap,final boolean SINGLE_SELECT,List<String> initFilter,Map<String,TreeSet<String>> selection) throws Exception  {
		super(parent, style);
		this.parent = parent;
		this.SINGLE_SELECT = SINGLE_SELECT;
		this.savedState = state;
		this.setInitFilter(initFilter);
		if(parent.getParent().getParent().getClass().getSimpleName().equals("Shell")) {
			SHELL_PARENT = true;
		}
		this.addDisposeListener(new DisposeListener() {
			@SuppressWarnings("rawtypes")
			public void widgetDisposed(DisposeEvent e) {
				data.disconnect();
				UnitBrowseDialogState latestState = new UnitBrowseDialogState();
				latestState.setData(new ArrayList<CategoryQueryModel>());
				latestState.getData().addAll(data.getData());
				latestState.setFilterData(new ArrayList<CategoryQueryModel>());
				latestState.getFilterData().addAll(data.getFilterData());
				latestState.setSavedSelectionIndex(dataTable.getSelectionIndices());
				Map<String,List<String>> filterData = new HashMap<String,List<String>>();
				Map<String,Set<String>> filterSelection = new HashMap<String,Set<String>>();
				for(BCombo bcombo:multiSet) {
					filterData.put(bcombo.getColumnName(),bcombo.getItems());
					filterSelection.put(bcombo.getColumnName(), bcombo.getSelection());
				}
				latestState.setSavedFilterData(filterData);
				latestState.setSavedFilterSelection(filterSelection);
				Map<String,UnitBrowseDialogState> stateMap = new HashMap<String,UnitBrowseDialogState>();
				for(BCombo bcombo:unitBrowseSet) {
					if(bcombo.getIndicator().getText().trim().equals("#")) {
						stateMap.put(bcombo.getColumnName(), ((UnitBrowseCombo) bcombo).getState());
					}
				}
				firePropertyChange("save_unit_browse_state",null,stateMap);
				Map<String,Map<String,Date>> dateSelection = new HashMap<String,Map<String,Date>>();
				for(BCombo bcombo:dateSet) {
					dateSelection.put(bcombo.getColumnName(), bcombo.getDateSelection());
				}
				latestState.setSavedDateSelection(dateSelection);
				
				Set<CategoryQueryModel> selectedModels = new HashSet<CategoryQueryModel>(); 
				for(TableItem item:dataTable.getSelection()) {
					selectedModels.add((CategoryQueryModel) item.getData());
				}
				JFilter<CategoryQueryModel> filter = new JFilter<CategoryQueryModel>(selectedModels, CategoryQueryModel.class);
				Map<String,TreeSet<String>> selection = new HashMap<String,TreeSet<String>>();
				Set<PrimaryKey> keySet = meta.RETURN_KEY;
				for(PrimaryKey pk:keySet) {
					for(Field field:pk.FIELD) {
						String column = field.getName(); 
						selection.put(column, (TreeSet<String>) filter.filter("{'$not':[{'get" + column + "':{'$sw':'?1'}}]}", "_iBear_").map(column).out(new TreeSet()));
					}
				}
				latestState.setSelection(selection);
				firePropertyChange("save_state",null,latestState);
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
		data = new CategoryQueryLink();
		data.toCategoryQueryModelList(null);
		if(getInitFilter()!=null) {
			data.filter("CategoryQueryCategory",getInitFilter());
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
			editor = new TableEditor(filterTable);
			editor.grabHorizontal = true;
			editor.horizontalAlignment = SWT.FILL;
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
		Button clear = new Button(filterTable, SWT.PUSH);
		clear.setText(" Reset ");
		clear.setToolTipText("Reset all filters");
		clear.setImage(clearIcon);
		editor.setEditor(clear,itemReset,0);
		clear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clear();
				dataTable.setSelection(-1);
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
				if(this.savedState.getSavedFilterData().get(meta.getField(bcombo).getName())!=null) {
					filter.setItems(this.savedState.getSavedFilterData().get(meta.getField(bcombo).getName()));
				}
				if(this.savedState.getSavedFilterSelection().get(meta.getField(bcombo).getName())!=null) {
					filter.setSelection(this.savedState.getSavedFilterSelection().get(meta.getField(bcombo).getName()));
				}
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
					public void propertyChange(PropertyChangeEvent e) {
						select();
					}
				});
				if(unitBorwseStateMap.containsKey(meta.getField(bcombo).getName())) {
					((UnitBrowseCombo) filter).getBrowseDialog().saveState(unitBorwseStateMap.get(meta.getField(bcombo).getName()));
					((UnitBrowseCombo) filter).getIndicator().setText("# ");
				}
				editor.setEditor(filter,filterItem,0);
				unitBrowseSet.add(filter);
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
				if(this.savedState.getSavedDateSelection().get(meta.getField(bcombo).getName())!=null) {
					filter.seDatetSelection(this.savedState.getSavedDateSelection().get(meta.getField(bcombo).getName()));
				}
				editor.setEditor(filter,filterItem,0);
				dateSet.add(filter);
			}			
		}
		data.setMultiSet(multiSet);
		data.setUnitSet(unitBrowseSet);
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
		if(this.SINGLE_SELECT) {
			dataViewer = new TableViewer(dataComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		} else {
			dataViewer = new TableViewer(dataComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.CHECK | SWT.VIRTUAL);
		}
		setTable(dataViewer.getTable());
		dataTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		dataViewer.setContentProvider(new ArrayContentProvider());
		dataViewer.setLabelProvider(new CategoryQueryLabelProvider());
		new TableColumn(dataTable, SWT.LEFT).setText("Product Category");
		new TableColumn(dataTable, SWT.LEFT).setText("Description");
		new TableColumn(dataTable, SWT.LEFT).setText("Name");
		
		for (int i = 0, n = dataTable.getColumnCount(); i < n; i++) {
			dataTable.getColumn(i).pack();
		}
		comparator = new CategoryQueryComparator();
		dataViewer.setComparator(comparator);
		dataTable.getColumn(0).addSelectionListener(getSelectionAdapter(dataTable.getColumn(0), 0));
		dataTable.getColumn(1).addSelectionListener(getSelectionAdapter(dataTable.getColumn(1), 1));
		dataTable.getColumn(2).addSelectionListener(getSelectionAdapter(dataTable.getColumn(2), 2));
		dataTable.getColumn(0).setWidth(170);
		dataTable.getColumn(1).setWidth(270);
		dataTable.getColumn(2).setWidth(270);
		
		dataTable.setLinesVisible(true);
		dataTable.setHeaderVisible(true);
		boolean SKIP = false;
		if(selection.size()>0) {
			data.doReduceUnitBrowse(selection);
			dataViewer.setInput(data.getFilterData());
			SKIP = true;
		} else {
			data.doExpandUnitBrowse();
			dataViewer.setInput(data.getFilterData());
			SKIP = true;
		}
		if(!SKIP) {
			if(this.savedState.getData().size()>0) {
				data.setFilterData(this.savedState.getFilterData());
				if(data.getFilterData().size()>0) {
					dataViewer.setInput(data.getFilterData());
				} else {
					dataViewer.setInput(data.getData());
				}
			} else {
				dataViewer.setInput(data.getData());
			}
		}
		sash.setWeights(new int[] {150, 730});
		progress = new ProgressBar(this, SWT.NONE);
		GridData gd_progress = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_progress.heightHint = 12;
		progress.setLayoutData(gd_progress);
		
		dataTable.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent event) {
				if(FIRST_FOCUS) {
					if(!SINGLE_SELECT) {
						resetSelectionIndex(getSavedState().getSavedSelectionIndex());
					} else {
						resetSingleSelectionIndex(getSavedState().getSavedSelectionIndex());
					}
				}
				FIRST_FOCUS = false;
			}
			@Override
			public void focusLost(FocusEvent e) {
			}
		});
		dataTable.setFocus();
	}
	private SelectionAdapter getSelectionAdapter(final TableColumn column,final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
						dataViewer.setInput(data.getFilterData());
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
					for(BCombo bcombo:unitBrowseSet) {
						UnitBrowseCombo ub = ((UnitBrowseCombo) bcombo);
						ub.getBrowseDialog().setSelection(ub.getBrowseSelection());
					}
				}
			}
		});
		job.schedule(); 	
	}
	private void selectSingle(boolean doubleClick) {
		firePropertyChange("selection_changed",null,Arrays.asList(dataTable.getSelection()));
		if(doubleClick) {
			firePropertyChange("close", "OPEN","CLOSE");
		}
	}
	private void resetSingleSelectionIndex(int[] selectionIndex) {
		if(selectionIndex!=null) {
			if(selectionIndex.length>0) {
				dataTable.setSelection(selectionIndex[0]);
			}
		}		
	}
	private void resetSelectionIndex(int[] selectionIndex) {
		if(selectionIndex!=null) {
			for(int i:selectionIndex) {
				dataTable.getItems()[i].setChecked(true);
			}
			selectCheckedItems(false);
		}
	}
	private void clear() {
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
						dataViewer.setInput(data.getFilterData());
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
					for(BCombo bcombo:unitBrowseSet) {
						UnitBrowseCombo ub = ((UnitBrowseCombo) bcombo);
						ub.getBrowseDialog().setSelection(ub.getBrowseSelection());
					}
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
//							reSetSelectionIndex();
						}
					});
				}
			}
		});
		job.schedule();
	}
	private void select() {
		
		itemCountBefore = dataTable.getItemCount();
		
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
						dataViewer.setInput(data.getFilterData());
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
					for(BCombo bcombo:unitBrowseSet) {
						UnitBrowseCombo ub = ((UnitBrowseCombo) bcombo);
						ub.getBrowseDialog().setSelection(ub.getBrowseSelection());
					}
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							if(itemCountBefore==dataTable.getItemCount()) {
								for(TableItem i:dataTable.getSelection()) {
									i.setChecked(true);
								}
							} else {
								dataTable.setSelection(-1);
							}
						}
					});
				}
			}
		});
		job.schedule(); 	
	}
	public Table getTable() {
		return dataTable;
	}
	public void setTable(Table table) {
		this.dataTable = table;
		dataTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if(!SINGLE_SELECT) {
					currentItem = (TableItem) event.item;
					selectCheckedItems(false);
				} else {
					selectSingle(false);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		}); 
		dataTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if(!SINGLE_SELECT) {
					selectCheckedItems(true);
				} else {
					selectSingle(true);
				}
			}
		});
	}
	private void selectCheckedItems(boolean mouse) {
		if(mouse) {
			if(currentItem.getChecked()) {
				currentItem.setChecked(false);
			} else {
				currentItem.setChecked(true);
			}
		}
		JFilter<TableItem> filter = new JFilter<TableItem>(new ArrayList<TableItem>(Arrays.asList(dataTable.getItems())),TableItem.class);
		List<TableItem> result = filter.filter("{'getChecked':{'$eq':'?1'}}",true).out(new ArrayList<TableItem>());
		dataTable.setSelection(result.toArray(new TableItem[result.size()]));
		firePropertyChange("selection_changed",null,result);
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
	public Table getDataTable() {
		return dataTable;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}
	@Override
	public void subTask(String name) {
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
	public CategoryQueryModel getSelected() {
		return selected;
	}
	public UnitBrowseDialogState getSavedState() {
		return savedState;
	}
	public void setSavedState(UnitBrowseDialogState savedState) {
		this.savedState = savedState;
	}
	public List<String> getInitFilter() {
		return initFilter;
	}
	public void setInitFilter(List<String> initFilter) {
		this.initFilter = initFilter;
	}
}
