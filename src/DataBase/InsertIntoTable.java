package DataBase;
import java.sql.*;

public class InsertIntoTable {

	private static final String USERNAME = "root";
	private static final String PASSWORD = "0000"; // my password
	private static final String CONN_STRING = "jdbc:mysql://localhost/users";

	public static void main(String[] args) throws SQLException {

		String query = "INSERT INTO user VALUES(1, 'demo', 'demopass')";

		try (Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate(query);
			System.out.println("Successfully updated the table.");
		}
	}
}
