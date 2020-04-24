package com.example.Library.DAO;

import com.example.Library.Model.HelpRequest;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HelpRequestDAOImpl implements HelpRequestDAO {

    private final String INSERT_REQUEST = "INSERT INTO help_requests (email, content) VALUES (?, ?)";
    private final String QUERY_REQUEST = "SELECT * FROM help_requests WHERE email = ?";
    private final String QUERY_REQUEST_FOR_LIBRARIAN = "SELECT * FROM help_requests WHERE email in " +
            "(SELECT user_email FROM observer_relations WHERE librarian_email = ?)";

    @Autowired
    JdbcTemplate template;

    public HelpRequestDAOImpl() {

    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    public Boolean addHelpRequest(HelpRequest requestHelp) {
        Object[] args = new Object[2];
        args[0] = requestHelp.getEmail();
        args[1] = requestHelp.getContent();

        try {
            if (template.update(INSERT_REQUEST, args) != 1) { return false; }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<HelpRequest> getRequestsForUser(String userEmail) {
        Object[] args = { userEmail };
        return template.query(QUERY_REQUEST, args, (ResultSetExtractor<List<HelpRequest>>) rs -> {
            List<HelpRequest> requests = new ArrayList<>();
            while (rs.next()) {
                requests.add(new HelpRequest(rs.getString("email"),
                                             rs.getString("content")));
            }
            return requests;
        });
    }

    @Override
    public List<HelpRequest> getRequestsForUser(User user) {
        return getRequestsForUser(user.getEmail());
    }

    @Override
    public List<HelpRequest> getRequestsForLibrarian(String librarianEmail) {
        return template.query(QUERY_REQUEST_FOR_LIBRARIAN, new Object[] { librarianEmail },
                              (ResultSetExtractor<List<HelpRequest>>) rs -> {
            List<HelpRequest> requests = new ArrayList<>();
            while (rs.next()) {
                requests.add(new HelpRequest(rs.getString("email"),
                                             rs.getString("content")));
            }
            return requests;
        });
    }

    @Override
    public List<HelpRequest> getRequestsForLibrarian(User librarian) {
        return getRequestsForLibrarian(librarian.getEmail());
    }
}
