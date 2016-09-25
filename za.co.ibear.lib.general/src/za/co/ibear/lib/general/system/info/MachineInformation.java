package za.co.ibear.lib.general.system.info;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class MachineInformation {
	
	public MachineInformation() {
	}

	public String getMackAddress() {
		StringBuilder sb;
		try {
			byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "UNKNOWN";
		}
		return sb.toString();
	}

	public String getUserName() {
		return System.getProperty("user.name");
	}

}
