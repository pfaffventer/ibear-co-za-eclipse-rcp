package za.co.ibear.snippet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the test harness class to test the close functionality in a dialog
 * box.
 * 
 * @author Debadatta Mishra(PIKU)
 * 
 */
public class TestDialogToHandleClose {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display, SWT.BORDER);

		// Create a button in the blank shell
		Button openBtn = new Button(shell, SWT.PUSH);
		String dialogBtnMsg = "Open a new Dialog";
		openBtn.setText(dialogBtnMsg);
		openBtn.setBounds(10, 10, dialogBtnMsg.length() * 8, 30);
		// Listener for the open Button
		openBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				new DialogToHandleClose(new Shell()).open();
			}
		});

		// Create a close button
		Button closeBtn = new Button(shell, SWT.PUSH);
		String closeBtnString = "Close shell";
		closeBtn.setText(closeBtnString);
		closeBtn.setBounds(10, 50, closeBtnString.length() * 8, 30);
		// Listener for the close button
		closeBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
			}
		});

		shell.setSize(400, 200);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}