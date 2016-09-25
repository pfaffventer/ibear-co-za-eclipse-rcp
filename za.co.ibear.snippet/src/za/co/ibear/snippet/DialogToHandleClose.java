package za.co.ibear.snippet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * This class is used to create a normal dialog box and it displays how to
 * handle the default close action.
 * 
 * @author Debadatta Mishra(PIKU)
 * 
 */
public class DialogToHandleClose extends Dialog {
	/**
	 * Default constructor
	 * 
	 * @param shell
	 *            of type {@link Shell}
	 */
	public DialogToHandleClose(Shell shell) {
		super(shell);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#setShellStyle(int)
	 */
	protected void setShellStyle(int arg0) {
		// Shell style with Minimize,Maximize and Close X button
		super.setShellStyle(SWT.MIN | SWT.MAX | SWT.CLOSE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	protected Control createDialogArea(Composite composite) {
		Composite area = (Composite) super.createDialogArea(composite);
		/*
		 * Place your UI components
		 */
		return area;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		// Handle default ok button
		MessageDialog.openInformation(new Shell(), "Information","You pressed ok button");
		super.okPressed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#cancelPressed()
	 */
	protected void cancelPressed() {
		// Handle default cancel button
		handleShellCloseEvent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#handleShellCloseEvent()
	 */
	protected void handleShellCloseEvent() {
		// Provide your specific implemention to handle the close event
		boolean flag = MessageDialog.openConfirm(new Shell(), "Confirmation","Are you sure to exit it");
		if (flag)
			super.handleShellCloseEvent();
		else
			open();
	}

}