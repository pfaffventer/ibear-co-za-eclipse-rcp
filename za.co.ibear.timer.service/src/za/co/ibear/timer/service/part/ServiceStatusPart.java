package za.co.ibear.timer.service.part;

import java.beans.PropertyChangeEvent;


import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.PersistState;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.wb.swt.ResourceManager;

import za.co.ibear.lib.email.HtmlMail;
import za.co.ibear.swt.control.table.ColumnTool;
import za.co.ibear.timer.service.ClearFileLog;
import za.co.ibear.timer.service.ServiceStatus;
import za.co.ibear.timer.service.StatusData;
import za.co.ibear.timer.service.swt.utility.SwtUtility;
import za.co.ibear.timer.service.StatusMessage;

public class ServiceStatusPart implements IProgressMonitor {

	private SimpleDateFormat stampFormat = new SimpleDateFormat("yyyy-MM-dd (hh:mm:ss)");

	private String myName = "Pack-house Automation Service";

	private final TrayItem trayItem = new TrayItem(Display.getDefault().getSystemTray(), SWT.NONE);
	final Shell waitShell = new Shell(SWT.APPLICATION_MODAL | SWT.TOOL | SWT.NO_TRIM | SWT.ON_TOP);
	private ServiceStatus status;

	private Button serverSwitch;
	private Button clearErrors;

	private ProgressBar progressBar;

	private TableViewer logViewer;
	private Table logTable;

	private Table errorTable;
	private TableViewer errorViewer;

	private Label statusLabel;
	private boolean CANCELED = false;
	private boolean CLOSE = false;
	private double worked = 0;

	private StatusData logData = new StatusData();

	private StatusData errorData = new StatusData();

	private StyledText textLog;

	private Image tick;
	private Image error;

	private long logMailTimer = 0;
	private static long logMailInterville = 600; //10 minutes = 600

	private Set<String> logErrors = new HashSet<String>();
	private String[] logMailAddresses = {"helpdesk@tru-cape.co.za","pfaff.venter@gmail.com"};

	private long hourlyTimer = 0;
	private static long hourlyInterville = 3600; //1 hour = 3600?

	@Inject EModelService modelService;
	@Inject MApplication application;
	@Inject UISynchronize sync;
	@Inject EPartService partService;
	@Inject IWorkbench workbench;

	@Inject
	public ServiceStatusPart() {
	}

	@PostConstruct
	public void postConstruct(final Composite parent) {

		tick = ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/tick.png");
		error = ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/error.gif");

		parent.setLayout(new GridLayout(3, false));
		SwtUtility.centreShell(parent.getDisplay(),(Shell) parent.getParent().getParent());
		((Shell) parent.getParent().getParent()).addShellListener(new ShellListener() {
			@Override
			public void shellIconified(ShellEvent e) {
				((Shell) parent.getParent().getParent()).setVisible(false);	
			}
			@Override public void shellActivated(ShellEvent e) {}
			@Override public void shellClosed(ShellEvent e) {}
			@Override public void shellDeactivated(ShellEvent e) {}
			@Override public void shellDeiconified(ShellEvent e) {}
		});
		trayItem.setImage(ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/file_change.gif"));
		trayItem.setToolTipText(myName);
		trayItem.addListener (SWT.Selection, new Listener () {
			@Override
			public void handleEvent (Event event) {
				if(((Shell) parent.getParent().getParent()).isVisible()) {
					((Shell) parent.getParent().getParent()).setVisible(false);	
				} else {
					((Shell) parent.getParent().getParent()).setMinimized(false);
					((Shell) parent.getParent().getParent()).setVisible(true);
				}
			}
		});
		serverSwitch = new Button(parent, SWT.NONE);
		serverSwitch.setText("New Button");
		serverSwitch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					switchServer();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Date start = new Date();
		status = new ServiceStatus(start,start,"Service start-up");
		status.addPropertyChangeListener("logMail",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				sync.syncExec(new Runnable() {
					@Override
					public void run() {
						try {
							serviceStatus();
							logMail();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		status.addPropertyChangeListener("hourlyRun",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				sync.syncExec(new Runnable() {
					@Override
					public void run() {
						hourlyRun();
					}
				});
			}
		});
		status.addPropertyChangeListener("push",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				logData.add((StatusMessage) e.getOldValue());
				sync.syncExec(new Runnable() {
					@Override
					public void run() {
						setLogViewerInput();
					}
				});
			}
		});
		status.addPropertyChangeListener("pop",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				logData.remove((StatusMessage) e.getOldValue());
				sync.syncExec(new Runnable() {
					@Override
					public void run() {
						setLogViewerInput();
					}
				});
			}
		});
		status.addPropertyChangeListener("error",new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent e) {
				sync.syncExec(new Runnable() {
					@Override
					public void run() {

						//clear log
						if(status.isClearScheduled()) {
							logErrors = new HashSet<String>();
							clearErrors.setEnabled(false);
							errorData = new StatusData();
							sync.syncExec(new Runnable() {
								@Override
								public void run() {
									setErrorViewerInput();
								}
							});
							status.setClearScheduled(false);
							return;
						}

						//populate log
						String m = ((Exception) e.getOldValue()).getMessage();
						if(!logErrors.contains(m)) {
							errorData.add(new StatusMessage(stampFormat.format(new Date().getTime()),"ERROR",m));
							sync.syncExec(new Runnable() {
								@Override
								public void run() {
									setErrorViewerInput();
								}
							});
						}
						logErrors.add(m);
						clearErrors.setImage(error);
						clearErrors.setEnabled(true);
					}
				});
			}
		});

		status.addPropertyChangeListener("logMessage",new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent e) {
				sync.syncExec(new Runnable() {
					@Override
					public void run() {
						textLog.setText(e.getOldValue().toString());
					}
				});
			}
		});

		status.setActive(false);
		serverSwitch.setImage(error);
		serverSwitch.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		serverSwitch.setText("Start");

		clearErrors = new Button(parent, SWT.NONE);
		clearErrors.setEnabled(false);
		clearErrors.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearErrors.setImage(tick);
				status.setClearScheduled(true);
				serverSwitch.setFocus();
			}
		});
		clearErrors.setImage(tick);
		clearErrors.setToolTipText("Clear errors");
		clearErrors.setText("Clear");

		progressBar = new ProgressBar(parent, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		logViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		logTable = logViewer.getTable();
		logTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		createViewerColumns(logViewer);
		logViewer.setContentProvider(new ArrayContentProvider());

		errorViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		errorTable = errorViewer.getTable();
		errorTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		createViewerColumns(errorViewer);
		errorViewer.setContentProvider(new ArrayContentProvider());

		textLog = new StyledText(parent, SWT.BORDER | SWT.V_SCROLL);
		textLog.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		textLog.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.stateMask == SWT.CTRL && e.keyCode == 97) { 
					textLog.selectAll();
				}
			}
		});
		textLog.setDoubleClickEnabled(true);
		textLog.setEditable(false);
		textLog.setText("Ftp Not Connected:");
		textLog.setData("style","font-style: italic;color: gray");

		statusLabel = new Label(parent, SWT.NONE);
		statusLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		setTaskName("Idle since (" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date()) + ")");
		createWaitShell();
	}

	public void setLogViewerInput(StatusData data) {
		this.logData = data;
		this.logViewer.setInput(null);
		this.logViewer.setInput(this.logData.toArrayList());
	}

	public void setLogViewerInput() {
		this.logViewer.setInput(null);
		this.logViewer.setInput(this.logData.toArrayList());
	}

	public void setErrorViewerInput() {
		this.errorViewer.setInput(null);
		this.errorViewer.setInput(this.errorData.toArrayList());
	}

	private void createViewerColumns(TableViewer viewer) {
		ColumnTool Ctool = new ColumnTool(viewer);

		TableViewerColumn column = Ctool.createTableViewerColumn("type",30);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return "";
			}

			@Override
			public Image getImage(Object element) {
				StatusMessage row = (StatusMessage) element;
				if(row.getType()=="ERROR") {
					return error;
				}
				return null;
			}

		});

		column = Ctool.createTableViewerColumn("stamp",170);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				StatusMessage row = (StatusMessage) element;
				return row.getStamp();
			}
		});
		column = Ctool.createTableViewerColumn("message",500);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				StatusMessage row = (StatusMessage) element;
				return row.getMessage();
			}
		});

	}

	private void createWaitShell() {
		waitShell.setLayout(new FillLayout(SWT.HORIZONTAL));
		waitShell.setSize(700, 70);
		SwtUtility.centreShell(Display.getDefault(),waitShell);
		Composite composite = new Composite(waitShell, SWT.BORDER);
		composite.setLayout(new GridLayout(1, false));
		Label comment = new Label(composite, SWT.NONE);
		comment.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		comment.setText("Waiting for scheduled processes.");
		ProgressBar progress = new ProgressBar(composite, SWT.HORIZONTAL | SWT.INDETERMINATE);
		progress.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	}

	@PersistState
	public void persistState() throws Exception {
		if(!CLOSE) {
			stop();
		} 
		waitShell.dispose();
	}

	@Persist
	public void save() {
	}

	@PreDestroy
	public void preDestroy() throws Exception {
		trayItem.dispose();
	}

	private String switchServer() throws Exception {
		return (status.isActive() ? stop() : start());
	}

	private String start() {
		logMailTimer = new Date().getTime();
		status.setActive(true);
		serverSwitch.setImage(ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/stop.gif"));
		serverSwitch.setText("Stop");
		serverSwitch.setToolTipText("Start pack-house integration");
		status.acivate(sync,modelService,application,partService);
		return serverSwitch.getText();
	}

	private String stop() throws Exception {
		setCanceled(true);
		status.setLastAction(new Date());
		status.setMessage("Service shut-down");
		status.setActive(false);
		serverSwitch.setImage(ResourceManager.getPluginImage("za.co.ibear.swt.icons", "icons/run_exc.gif"));
		serverSwitch.setText("Start");
		serverSwitch.setToolTipText("Stop pack-house integration");
		Display display = Display.getDefault();
		waitShell.open();
		while (!waitShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return "STOPPED";
	}

	@Focus
	public void onFocus() throws Exception {
		switchServer();
	}

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceStatus serverStatus) {
		this.status = serverStatus;
	}

	@Override
	public void beginTask(final String name, final int totalWork) {
		sync.syncExec(new Runnable() {
			@Override
			public void run() {
				setTaskName(name); 
				progressBar.setMaximum(totalWork);
				progressBar.setToolTipText(name);
			}
		});
	}

	@Override
	public void done() {
		if(status.isActive()) {
			status.acivate(sync,modelService,application,partService);
		}
		sync.syncExec(new Runnable() {
			@Override
			public void run() {
				progressBar.setMaximum(0);
				progressBar.setSelection(0);
				setTaskName("Idle since (" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date()) + ")");
				progressBar.setToolTipText("");
			}
		});
	}

	@Override
	public void internalWorked(double work) {
		setWorked(work);
		if(work==99) {
			sync.syncExec(new Runnable() {
				@Override
				public void run() {
					CLOSE = true;
					workbench.close();
				}
			});
		}
	}

	@Override
	public boolean isCanceled() {
		return CANCELED;
	}

	@Override
	public void setCanceled(final boolean value) {
		sync.syncExec(new Runnable() {
			@Override
			public void run() {
				CANCELED=value;
			}
		});
	}

	@Override
	public void setTaskName(final String name) {
		sync.syncExec(new Runnable() {
			@Override
			public void run() {
				statusLabel.setText(name);
			}
		});
	}

	@Override
	public void subTask(final String name) {
		sync.syncExec(new Runnable() {
			@Override
			public void run() {
				setTaskName(name);
			}
		});
	}

	@Override
	public void worked(final int work) {
		sync.syncExec(new Runnable() {
			@Override
			public void run() {
				progressBar.setSelection(progressBar.getSelection() + work);
			}
		});
	}

	public double getWorked() {
		return worked;
	}

	public void setWorked(double worked) {
		this.worked = worked;
	}

	public StatusData getData() {
		return logData;
	}

	public void setData(StatusData data) {
		this.logData = data;
	}

	private void serviceStatus() throws Exception {
		
		p("serviceStatus()");
		
//		MsSqlServerJdbc db = new MsSqlServerJdbc(false);
//		String q = "update "
//				+ ServiceStatusSchema.FULL_NAME
//				+ " set " + StockColumn.PROCESS_DATE.split(" ")[0] + " = '" + new String(new Date().getTime()) + "'"
//				+ " where " + ImportLogColumn.KEY.split(" ")[0] + " = 'service'"
//				+ " and " + ImportLogColumn.VALUE.split(" ")[0] + " = 'last_activity'";
//		db.update(q);
//		db.disconnect();
	}

	private void hourlyRun() {
		long now = new Date().getTime();
		long diff = (now - hourlyTimer) / 1000;

		if(diff>=hourlyInterville) {
			try {
				new ClearFileLog();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hourlyTimer = new Date().getTime();
		}
	}

	private void logMail() throws Exception {
		long now = new Date().getTime();
		long diff = (now - logMailTimer) / 1000;
		if(diff>=logMailInterville) {
			if(logErrors.size()>0) {
				String body = "<H3>" + myName + " Error Log:</H3>";
				body = body + "<table border=1 style=font-family:courier new;font-size:14px;><tr><th>Message</th></tr>";
				for(String m:logErrors) {
					body = body + "<tr><td>" + m + "</td></tr>";
				}
				body = body + "</table><footer><p>Load Time: " + stampFormat.format(new Date()) + "</p></footer>";
				HtmlMail mail = new HtmlMail(myName,body,logMailAddresses);
				mail.send();
			}
			logMailTimer = new Date().getTime();
		}
	}

	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":) " + v);
	}

}
