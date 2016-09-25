package za.co.ibear.timer.service.swt.utility;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class SwtUtility {
	
	public static void centreShell(Display display, Shell shell) {
		Monitor monitor = display.getPrimaryMonitor();
		Rectangle monitorRect = monitor.getBounds();
		Rectangle shellRect = shell.getBounds();
		int x = monitorRect.x + (monitorRect.width - shellRect.width) / 2;
		int y = monitorRect.y + (monitorRect.height - shellRect.height) / 4;
		shell.setLocation(x, y);
	}
	
	public static void positionBottomRight(Display display, Shell shell) {
		Monitor monitor = display.getPrimaryMonitor();
		Rectangle monitorRect = monitor.getBounds();
		Rectangle shellRect = shell.getBounds();

		int x = monitorRect.x + (monitorRect.width - shellRect.width) - 20;
		int y = monitorRect.y + (monitorRect.height - shellRect.height) - 100;
		
		shell.setLocation(x, y);
	}
	

}
