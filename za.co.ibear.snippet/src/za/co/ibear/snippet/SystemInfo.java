package za.co.ibear.snippet;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class SystemInfo{
 
   public static void main(String[] args){
 
	InetAddress ip;
	try {

//		System.out.println("Time millis : " + System.currentTimeMillis());
//		System.out.println("Time nano : " + System.nanoTime() + "\n\n\n");

		long s = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
		System.out.println("Time s (seconds) : " + s);

//		Thread.sleep(3000);

		long e = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
		System.out.println("Time e (seconds) : " + e);
		
		System.out.println("Diff             : " + (e-s) + "\n\n\n");
		
		System.out.println("Operating system version =>"+ System.getProperty("user.name") + "\n");
		
		
		
		
		
		ip = InetAddress.getLocalHost();
		System.out.println("Current host name : " + ip.getHostName());
		System.out.println("Current IP address : " + ip.getHostAddress());
		String nameOS= System.getProperty("os.name");
		System.out.println("Operating system Name=>"+ nameOS);
		String osType= System.getProperty("os.arch");
		System.out.println("Operating system type =>"+ osType);
		String osVersion= System.getProperty("os.version");
		System.out.println("Operating system version =>"+ osVersion);
		
		System.out.println(System.getenv("PROCESSOR_IDENTIFIER"));
		System.out.println(System.getenv("PROCESSOR_ARCHITECTURE"));
		System.out.println(System.getenv("PROCESSOR_ARCHITEW6432"));
		System.out.println(System.getenv("NUMBER_OF_PROCESSORS"));
		/* Total number of processors or cores available to the JVM */
    System.out.println("Available processors (cores): " + 
        Runtime.getRuntime().availableProcessors());

    /* Total amount of free memory available to the JVM */
    System.out.println("Free memory (bytes): " + 
        Runtime.getRuntime().freeMemory());

    /* This will return Long.MAX_VALUE if there is no preset limit */
    long maxMemory = Runtime.getRuntime().maxMemory();
    /* Maximum amount of memory the JVM will attempt to use */
    System.out.println("Maximum memory (bytes): " + 
        (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

    /* Total memory currently in use by the JVM */
    System.out.println("Total memory (bytes): " + 
        Runtime.getRuntime().totalMemory());
		
		
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
 
		byte[] mac = network.getHardwareAddress();
 
		System.out.print("Current MAC address : ");
 
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
		System.out.println(sb.toString());
 
	} catch (UnknownHostException e) {
 
		e.printStackTrace();
 
	} catch (SocketException e){
 
		e.printStackTrace();
 
	}
	catch (Exception e){
 
		e.printStackTrace();
 
	}
 
   }
 
}