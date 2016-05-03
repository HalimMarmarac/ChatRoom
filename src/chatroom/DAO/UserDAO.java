package chatroom.DAO;

import java.sql.*;
import DataBase.ConnectionManager;
import chatroom.DTO.User;

public class UserDAO implements UserDaoInterface {
	// connect to the database
	Connection connection = ConnectionManager.getInstance().getConnection();

	// add new user to database
	public void addUser(String username, String password) throws SQLException {

		PreparedStatement statement = connection.prepareStatement("INSERT INTO user(username, password) VALUES ('" + username + "','" + password + "');");
		statement.executeUpdate();

	}

	// get user data from database
	public User getUser(String username, String password) throws SQLException {

		User user = null;

		String query = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "';";
		ResultSet rs = null;

		try (PreparedStatement statement = connection.prepareStatement(query);) {
			rs = statement.executeQuery();
			while (rs.next()) {
				user = new User(rs.getString("username"), rs.getString("password"));
			}
		}

		return user;
	}
	
	// get just username from database
	public User getUserInfo(String username) throws SQLException {
		
		User user = null;

		String query = "SELECT * FROM user WHERE username = '" + username + "';";
		ResultSet rs = null;

		try (PreparedStatement statement = connection.prepareStatement(query);) {
			rs = statement.executeQuery();
			while (rs.next()) {
				user = new User(rs.getString("username"));
			}
		}
		
		return user;
	}

}
