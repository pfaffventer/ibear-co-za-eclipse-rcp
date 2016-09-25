package za.co.ibear.code.data.dictionary.system.database;

import za.co.ibear.lib.jdbc.utility.ConnectionRecord;

public class DatabaseConstant {

//	public static ConnectionRecord DB_CONNECTION = new ConnectionRecord("jdbc:postgresql://localhost:5432/","ibear","postgres","password");
//	public static ConnectionRecord DB_ROOT_CONNECTION = new ConnectionRecord("jdbc:postgresql://localhost:5432/","","postgres","password");

	public static ConnectionRecord DB_CONNECTION = new ConnectionRecord("jdbc:sqlite:ibear.db");
	public static ConnectionRecord DB_ROOT_CONNECTION = new ConnectionRecord("jdbc:sqlite:ibear.db");

	public static String DB_DATE_FORMAT = "yyyy-MM-dd";
	public static String DB_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
	
	
}
