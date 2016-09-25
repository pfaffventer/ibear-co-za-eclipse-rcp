
package za.co.ibear.code.gizmo.ui.handler;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import za.co.ibear.code.gizmo.ui.part.ReverseEngineerPart;

public class OpenFile {

	@Inject EPartService partService;

	@Execute
	public void execute(Shell shell) throws IOException {
		File path = new File("D:/neon/rcp-neon/rcp-workspace/factory");
		FileDialog dialog = new FileDialog(shell,SWT.SINGLE);
		dialog.setFilterPath(path.getCanonicalPath());
		if(dialog.open()==null) {
			dialog = null;
			return;
		}
		ReverseEngineerPart part = (ReverseEngineerPart) partService.findPart("za.co.ibear.code.gizmo.ui.part.reverse.engineer").getObject();
		File file = new File(dialog.getFilterPath() + File.separator + dialog.getFileName());
		part.getEngineer().setFile(file);
	}

	protected static void p(String v) {
		System.out.println("OpenFile:) " + v);
	}

}