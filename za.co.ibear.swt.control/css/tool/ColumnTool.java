package za.co.ibear.swt.control.table.tool;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;

public class ColumnTool {
	
	private TableViewer viewer;
	
	public ColumnTool(TableViewer viewer) {
		this.viewer = viewer;
	}
	
	public TableViewerColumn createTableViewerColumn(String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	public TableViewerColumn createTableViewerColumnCheck(String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,SWT.CHECK);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

}
