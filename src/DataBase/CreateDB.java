package DataBase;
import java.sql.*;

public class CreateDB {
	
	private static final String USERNAME = "root";
	private static final String PASSWORD = "0000"; // my password
	private static final String CONN_STRING = "jdbc:mysql://localhost/";

	public static void main(String[] args) throws SQLException {

		String sqlQuery = "CREATE DATABASE users";

		try (Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate(sqlQuery);
			System.out.println("Successfully created the database.");
		}
	}
}
