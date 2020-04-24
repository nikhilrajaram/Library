package com.example.Library.DAO;

import com.example.Library.Model.Librarian;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class LibrarianDataDAOImpl implements LibrarianDataDAO {

    private final String QUERY_EMAIL = "SELECT email FROM librarians WHERE email = ?";
    private final String QUERY_LIBRARIAN = "SELECT email, password, enabled FROM librarians WHERE email = ?";
    private final String INSERT_LIBRARIAN = "INSERT INTO librarians (email, password, enabled) VALUES (?, ?, ?)";
    private final String INSERT_AUTHORITY = "INSERT INTO authorities (email, authority) VALUES (?, 'LIBRARIAN')";
    private final String QUERY_CREDENTIALS = "SELECT password FROM librarians WHERE email = ?";

    @Autowired
    JdbcTemplate template;

    public LibrarianDataDAOImpl(){

    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Boolean isEmailAvailable(String email) {
        Object[] args = new Object[1];
        args[0] = email;
        return template.query(QUERY_EMAIL, args, (ResultSetExtractor<Boolean>) rs -> !rs.next());
    }

    @Override
     public Librarian getLibrarian(String email) {
        Object[] args = {email};
        return template.query(QUERY_LIBRARIAN, args, (ResultSetExtractor<Librarian>) rs -> {
            if (!rs.next()) { return null; }

            return new Librarian(rs.getString("email"),
                    rs.getString("password"),
                    rs.getBoolean("enabled"));

        });
    }

    @Override
    public Boolean registerLibrarian(Librarian librarian) {
        Object[] args = new Object[3];
        args[0] = librarian.getEmail();
        args[1] = librarian.getPassword();
        args[2] = librarian.isEnabled();

        /** Yet to complete */
        return null;
    }

    @Override
    public String getBcryptPassword(Librarian librarian) {
        Object[] args = new Object[1];
        args[0] = librarian.getEmail();
        return template.query(QUERY_CREDENTIALS, args, (ResultSetExtractor<String>) rs -> {
            if (!rs.next()) return "";

            return rs.getString("password");
        });
    }

}
