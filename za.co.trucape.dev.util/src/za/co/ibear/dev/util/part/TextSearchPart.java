 
package za.co.ibear.dev.util.part;

import java.lang.reflect.InvocationTargetException;




import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Button;

import za.co.ibear.lib.global.database.Jdbc;
import za.co.ibear.lib.jdbc.microsoft.MsSqlServerJdbc;
import za.co.ibear.swt.control.table.ColumnTool;



public class TextSearchPart {
	private Text search;
	private TableViewer viewer;
	private Table table;
	private StyledText output;

	private List<SchemaRecord> schemas = new ArrayList<SchemaRecord>();
	
	private String schemas_in = "";
	private String search_txt = "";
	
	@Inject
	public TextSearchPart() {
		//TODO Your code here
	}
	
	@PostConstruct
	public void postConstruct(final Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		
		initialData();		
		
		search = new Text(parent, SWT.BORDER);
		search.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnSearch = new Button(parent, SWT.NONE);
		btnSearch.setText("Search");
		
		btnSearch.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						monitor.beginTask("Searching all columns in database", 5700);
						MsSqlServerJdbc db = null;
						try {
							db = new MsSqlServerJdbc(Jdbc.DB_DEBUG);
							populate(db);
							ResultSet rs = db.result("select * from aaa.t_search_all");
							while(rs.next()) {
								String q = rs.getString("query").trim();
								monitor.worked(1);
								monitor.subTask(q);
								execute(q,db);
								if(monitor.isCanceled()) {
									monitor.done();
									rs.close();
									return;
								}
							}
							rs.close();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								db.disconnect();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}	
						monitor.done();
					}
				};
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(parent.getShell());
				try {
					dialog.run(true, true, runnableWithProgress);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});    

		Composite schemaSelect = new Composite(parent, SWT.NONE);
		schemaSelect.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_schemaSelect = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_schemaSelect.heightHint = 201;
		schemaSelect.setLayoutData(gd_schemaSelect);
		
		viewer = new TableViewer(schemaSelect, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
		table = viewer.getTable();
		viewer.setContentProvider(new ArrayContentProvider());
		createColumn();
		viewer.setInput(schemas);
		
		output = new StyledText(parent, SWT.BORDER);
		output.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		output.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.stateMask == SWT.CTRL && e.keyCode == 97) { 
					output.selectAll();
				}
			}
		});

	}

	private void populate(MsSqlServerJdbc db) throws Exception {

		
		schemas_in = "";
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				search_txt = search.getText().trim();
				for(TableItem i:table.getItems()) {
					if(i.getChecked()) {
						schemas_in = schemas_in + "'" + i.getText().trim() + "',";
					}
				}
			}
		});

		
		db.drop("drop table aaa.t_search_all");
		db.update("create table aaa.t_search_all (query nvarchar(1000),table_name nvarchar(130),schema_name nvarchar(130))");
		db.update("insert into aaa.t_search_all select 'select [' + columns.name + '] from [' + schemas.name + '].[' + tables.name + '] where [' +columns.name + '] like ''%" + search_txt + "%''',tables.name,schemas.name from sys.tables tables join sys.schemas schemas on(tables.schema_id = schemas.schema_id) join sys.columns columns on (tables.object_id = columns.object_id) join sys.types types on (columns.system_type_id = types.system_type_id) where tables.type_desc ='USER_TABLE' and types.name IN ('char', 'nchar', 'nvarchar', 'varchar')");

		String delete = "delete from aaa.t_search_all where schema_name not in (" + schemas_in.substring(0,schemas_in.length()-1) + ")";
		
		db.update(delete);

	}
	
	private void execute(final String query,MsSqlServerJdbc db) throws Exception {
		ResultSet rsExe;
		try {
			rsExe = db.result(query);
			if(rsExe.next()) {
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						output.setText(output.getText() + query + "\n");
					}
				});
			}
			rsExe.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void initialData() {
		MsSqlServerJdbc init_db = null;
		try {
			init_db = new MsSqlServerJdbc(Jdbc.DB_DEBUG);
			ResultSet rs_init = init_db.result("select * from sys.schemas order by name;");
			while(rs_init.next()) {
//				schemas.add(rs_init.getString("name").trim());
				
				schemas.add(new SchemaRecord(rs_init.getString("name").trim(),rs_init.getString("schema_id").trim()));
				
			}
			rs_init.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				init_db.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	
	private void createColumn() {
		ColumnTool Ctool = new ColumnTool(viewer);
		TableViewerColumn column = Ctool.createTableViewerColumn("Select Schema(s)",400);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((SchemaRecord) element).getName();
			}
		});
		
		column = Ctool.createTableViewerColumn("Schema id",150);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((SchemaRecord) element).getSchema_id();
			}
		});
		
	}
	
	protected void p(String l) {
		System.out.println(this.getClass().getSimpleName() + ":) " + l);
	}

}

class SchemaRecord {
	String name;
	String schema_id;

	public SchemaRecord(String name, String schema_id) {
		super();
		this.name = name;
		this.schema_id = schema_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSchema_id() {
		return schema_id;
	}
	
	public void setSchema_id(String schema_id) {
		this.schema_id = schema_id;
	}
	
}