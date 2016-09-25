package za.co.ibear.snippet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public final class DialogWithNoButtonsAtAll extends Dialog {

	public DialogWithNoButtonsAtAll(Shell shell) {
		super(shell);
	}

	protected void setShellStyle(int arg0) {
		super.setShellStyle(SWT.TITLE);
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText(
				"A dialog box with no buttons at all press 'ESC' to close");
		try {
			composite.setLayout(new FormLayout());
			{
				createLabel(composite);
				createTextField(composite);
				createButton(composite);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		composite.getShell().setSize(300, 100);
		setDialogLocation();
		return composite;
	}

	protected Button createButton(Composite arg0, int arg1, String arg2,
			boolean arg3) {
		return null;
	}

	private void createLabel(Composite composite) {
		Label label = new Label(composite, SWT.None);
		label.setText("Label 1");
		FormData lblData = new FormData();
		lblData.width = 40;
		lblData.height = 20;
		lblData.left = new FormAttachment(0, 1000, 6);// x co-ordinate
		lblData.top = new FormAttachment(0, 1000, 17);// y co-ordinate
		label.setLayoutData(lblData);
	}

	private void createTextField(Composite composite) {
		Text text = new Text(composite, SWT.None);
		text.setText("Some text data");
		FormData txtData = new FormData();
		txtData.width = 100;
		txtData.height = 20;
		txtData.left = new FormAttachment(0, 1000, 50);// x co-ordinate
		txtData.top = new FormAttachment(0, 1000, 17);// y co-ordinate
		text.setLayoutData(txtData);
	}

	private void createButton(Composite composite) {
		Button btn = new Button(composite, SWT.PUSH);
		btn.setText("Press to close");
		FormData btnData = new FormData();
		btnData.width = 90;
		btnData.height = 20;
		btnData.left = new FormAttachment(0, 1000, 100);// x co-ordinate
		btnData.top = new FormAttachment(0, 1000, 40);// y co-ordinate
		btn.setLayoutData(btnData);
		btn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				close();
			}
		});
	}

	private void setDialogLocation() {
		Rectangle monitorArea = getShell().getDisplay().getPrimaryMonitor()
				.getBounds();
		Rectangle shellArea = getShell().getBounds();
		int x = monitorArea.x + (monitorArea.width - shellArea.width) / 2;
		int y = monitorArea.y + (monitorArea.height - shellArea.height) / 2;
		getShell().setLocation(x,y);
	}
}