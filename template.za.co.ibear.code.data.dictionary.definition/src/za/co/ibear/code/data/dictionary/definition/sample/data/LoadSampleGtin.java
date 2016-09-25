package za.co.ibear.code.data.dictionary.definition.sample.data;

import java.io.BufferedReader;

import java.sql.PreparedStatement;
import java.io.File;
import java.io.FileReader;

import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;
import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;

public class LoadSampleGtin {

	public LoadSampleGtin() throws Exception {
		SqliteJdbc db = null;
		try {
			db = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);

			PreparedStatement statement = db.getConnection().prepareStatement("insert into Gtin values (?,?,?,?,?,?,?,?,?,?)");

			BufferedReader reader = new BufferedReader(new FileReader(new File("D:/local/e-kepler(RCP)/e4-ibear/workspace/za.co.ibear.playground/files/data/data.ld")));
			String line;
			while ((line=reader.readLine())!=null) {

				String[] values = line.split(",");

				statement.setString(1, values[0].trim());
				statement.setString(2, values[1].trim());
				statement.setString(3, values[2].trim());
				statement.setString(4, values[3].trim());
				statement.setString(5, values[4].trim());
				statement.setString(6, values[5].trim());
				statement.setString(7, values[6].trim());
				statement.setString(8, values[7].trim());

				statement.setString(9, "AUTO");

				statement.setString(10,"2014-01-10 12:00:00");
				statement.executeUpdate();

				p(line);
			
			}
			reader.close();

			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disconnect();
		}

	}
	
	
	public static void main(String[] args) {
		try {
			new LoadSampleGtin();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":) " + v);
	}

}
