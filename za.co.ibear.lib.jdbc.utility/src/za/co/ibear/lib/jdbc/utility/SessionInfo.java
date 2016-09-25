package za.co.ibear.lib.jdbc.utility;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class SessionInfo {
	
	private long sessionStart = System.nanoTime();
	private String userName = System.getProperty("user.name");
	private String macAddress = null;
	
	public SessionInfo() throws Exception {
		byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
		macAddress = sb.toString();
	}

	public long getSessionStart() {
		return sessionStart;
	}

	public String getUserName() {
		return userName;
	}

	public String getMacAddress() {
		return macAddress;
	}

}
