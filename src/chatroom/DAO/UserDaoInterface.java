package chatroom.DAO;

import java.sql.SQLException;
import chatroom.DTO.User;

public interface UserDaoInterface {

	public void addUser(String username, String password) throws SQLException;
	public User getUser(String username, String password) throws SQLException;
	public User getUserInfo(String username) throws SQLException;
}
