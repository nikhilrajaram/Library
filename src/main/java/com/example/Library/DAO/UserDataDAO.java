package com.example.Library.DAO;

import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Types;

@Service
public class UserDataDAO implements UserDataDAOImpl {

    private final String QUERY_EMAIL = "SELECT email FROM users WHERE email = ?";
    private final String QUERY_NEW_UID = "SELECT MAX(uid) FROM users";
    private final String INSERT_USER = "INSERT INTO users (uid, email, password) VALUES (?, ?, ?)";
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
    public Integer getNewUid() {
        return template.query(QUERY_NEW_UID, (ResultSetExtractor<Integer>) rs -> {
            if (!rs.next()) {
                return 0;
            }

            return rs.getInt("uid")+1;
        });
    }

    @Override
    public Boolean registerUser(User user) {
        Object[] args = new Object[3];
        args[0] = user.getUid();
        args[1] = user.getEmail();
        args[2] = user.getPassword();

        try {
            return template.update(INSERT_USER, args) == 1;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
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
