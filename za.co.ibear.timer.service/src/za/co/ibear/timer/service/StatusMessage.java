package za.co.ibear.timer.service;

public class StatusMessage {
	
	private String stamp;
	private String type;
	private String message;
	
	public StatusMessage(String stamp,String type,String message) {
		super();
		this.stamp = stamp;
		this.type = type;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
