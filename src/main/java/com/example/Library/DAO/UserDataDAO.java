package com.example.Library.DAO;

import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class UserDataDAO implements UserDataDAOImpl {

    private final String QUERY_EMAIL = "SELECT email FROM users WHERE email = ?";
    private final String QUERY_USER = "SELECT email, password, enabled FROM users WHERE email = ?";
    private final String INSERT_USER = "INSERT INTO users (email, password, enabled) VALUES (?, ?, ?)";
    private final String INSERT_AUTHORITY = "INSERT INTO authorities (email, authority) VALUES (?, 'USER')";
    private final String QUERY_CREDENTIALS = "SELECT password FROM users WHERE email = ?";

    @Autowired
    JdbcTemplate template;

    public UserDataDAO() {

    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Boolean isEmailAvailable(String email) {
        Object[] args = new Object[1];
        args[0] = email;
        return template.query(QUERY_EMAIL, args, (ResultSetExtractor<Boolean>) rs -> rs.next());
    }

    @Override
    public User getUser(String email) {
        Object[] args = {email};
        return template.query(QUERY_USER, args, (ResultSetExtractor<User>) rs -> {
            if (!rs.next()) { return null; }

            return new User(rs.getString("email"),
                            rs.getString("password"),
                            rs.getBoolean("enabled"));

        });
    }

    @Override
    public Boolean registerUser(User user) {
        Object[] args = new Object[3];
        args[0] = user.getEmail();
        args[1] = user.getPassword();
        args[2] = user.isEnabled();

        try {
            if (template.update(INSERT_USER, args) != 1) { return false; }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

        try {
            if (template.update(INSERT_AUTHORITY, user.getEmail()) != 1) { return false; }
        } catch (DataAccessException e) {
            e.printStackTrace();
            System.err.println("User was added into users table but not authorities."); // log message when set up
        }

        return false;
    }

    @Override
    public String getBcryptPassword(User user) {
        Object[] args = new Object[1];
        args[0] = user.getEmail();
        return template.query(QUERY_CREDENTIALS, args, (ResultSetExtractor<String>) rs -> {
            if (!rs.next()) return "";

            return rs.getString("password");
        });
    }
}
