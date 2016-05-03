package DataBase;
import java.sql.*;

public class CreateTable {

	private static final String USERNAME = "root";
	private static final String PASSWORD = "0000"; // my password
	private static final String CONN_STRING = "jdbc:mysql://localhost/users";

	public static void main(String[] args) throws SQLException {

		String query = "CREATE TABLE user (ID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, username VARCHAR(50), password VARCHAR(16));";
		
		try (Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate(query);
			System.out.println("Successfully created table.");
		}
	}
}