package com.example.Library.DAO;

import com.example.Library.Model.HelpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class HelpRequestDAOImpl implements HelpRequestDAO {

    private final String INSERT_REQUEST = "INSERT INTO help_requests (email, content) VALUES (?, ?)";
    private final String QUERY_REQUEST = "SELECT email FROM help_requests WHERE email = ?";

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
    public HelpRequest getRequest(String email) {
        Object[] args = {email};
        return template.query(QUERY_REQUEST, args, (ResultSetExtractor<HelpRequest>) rs -> {
            if (!rs.next()) { return null; }

            return new HelpRequest(rs.getString("email"),
                                   rs.getString("content"));

        });

    }
}
