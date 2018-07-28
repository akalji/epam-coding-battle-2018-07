package com.epam.server.dao;

import com.epam.server.model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class UserDaoImpl {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger log = LogManager.getLogger(UserDaoImpl.class);
    private static final String ADD_USER = "INSERT INTO users (user_name, user_email, user_password, user_requests) VALUES(?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT  user_id, user_name, user_name, user_email, user_password, user_requests FROM users WHERE user_id=?";
    private static final String GET_USER_BY_EMAIL_AND_PASSWORD = "SELECT user_id, user_name, user_name, user_email, user_password, user_requests FROM users WHERE user_email=? and user_password=?";

    public User getUserById(int id) {
        Connection conn = connectionPool.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            User user;
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
                String password = rs.getString("user_password");
                user.setPassword(password);
                user.setEmail(rs.getString("user_email"));
                user.setRequests(rs.getInt("user_requests"));

                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, e);
        } finally {
            closeResources(stmt, rs, conn);
        }
        return null;
    }

    public boolean addUser(User user) {
        Connection conn = connectionPool.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setObject(3, user.getPassword());
            stmt.setInt(4, user.getRequests());

            int result = stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getInt(1));
            }

            return (result > 0);
        } catch (SQLException e) {
            log.log(Level.ERROR, e);
        } finally {
            closeResources(stmt, rs, conn);
        }

        return false;
    }

    public boolean checkForEmailAndPassword(String email, String password) {
        Connection conn = connectionPool.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(GET_USER_BY_EMAIL_AND_PASSWORD);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            return rs.next()
                    && rs.getString("user_email").equals(email)
                    && rs.getString("user_password").equals(password);
        } catch (SQLException e) {
            log.log(Level.ERROR, e);
        } finally {
            closeResources(stmt, rs, conn);
        }
        return false;
    }

    private void closeResources(PreparedStatement statement, ResultSet resultSet, Connection connection) {
        try {
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
            if (connection != null) connection.close();
        } catch (SQLException e1) {
            log.log(Level.ERROR, e1);
            e1.printStackTrace();
        }
    }

}
