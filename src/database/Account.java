package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.crypto.provider.RSACipher;

public class Account {
	private Connection conn;

	public Account(Connection conn) {
		this.conn = conn;
	}

	public boolean login(String email, String password) throws SQLException {
		String sql = "select count(*) as count from users where email=? and password=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, password);
		ResultSet resultSet = statement.executeQuery();
		int count = 0;
		if (resultSet.next()) {
			count = resultSet.getInt("count");
		}
		resultSet.close();
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void create(String email, String password) throws SQLException {
		String sql = "insert into users (email, password) values (?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, password);
		statement.executeUpdate();
		statement.close();
	}
	
	public boolean exists(String email) throws SQLException {
		String sql = "select count(*) as count from users where email=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, email);
		ResultSet resultSet = statement.executeQuery();
		int count = 0;
		if(resultSet.next()) {
			count = resultSet.getInt("count");
		}
		resultSet.close();
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
}
