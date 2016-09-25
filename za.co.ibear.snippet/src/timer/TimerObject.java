package timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerObject {

	Timer timer;
	String name = null;

	public TimerObject(String name) {
		this.name = name;
		timer = new Timer();
		timer.schedule(new RemindTask(), 0, 3 * 1000);
		
		p("\n\n___<>: " + name);

	}

	class RemindTask extends TimerTask {
		public void run() {
			p(" run() __: " + name);
		}
	}
	
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}

}
