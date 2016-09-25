package timer;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;

public class TimerTest extends Shell {

	Set<TimerObject> map = new HashSet<TimerObject>();
	
	int n = 0;

	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			TimerTest shell = new TimerTest(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TimerTest(Display display) {
		super(display, SWT.SHELL_TRIM);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if(map.size()>0) {
					map.iterator().next().timer.cancel();
					map.remove(map.iterator().next());
				}
			}
		});
		setLayout(new GridLayout(1, false));
		
		Button test = new Button(this, SWT.NONE);
		test.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(map.size()>0) {
					map.iterator().next().timer.cancel();
					map.remove(map.iterator().next());
				}
				map.add(new TimerObject("timer <:" + n));
				n ++;
			}
		});
		test.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		test.setText("TEST");
		createContents();
	}

	protected void createContents() {
		setText("SWT Application");
		setSize(450, 154);
		map.add(new TimerObject("timer <:" + n++));
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
