package za.co.ibear.lib.jdbc.utility;

public class ConnectionRecord {

	private String url = null;
	private String databasName = null;

	private String user = null;
	private String password = null;

	public ConnectionRecord(String url, String dbName, String user, String password) {
		super();
		this.url = url;
		this.databasName = dbName;
		this.user = user;
		this.password = password;
	}

	public ConnectionRecord(String url) {
		super();
		this.url = url;
		this.databasName = "";
		this.user = "";
		this.password = "";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabasName() {
		return databasName;
	}

	public void setDatabasName(String databasName) {
		this.databasName = databasName;
	}

}
