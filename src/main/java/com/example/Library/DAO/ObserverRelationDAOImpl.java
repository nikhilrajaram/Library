package com.example.Library.DAO;

import com.example.Library.Model.ObserverRelation;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.ArrayList;
import java.util.List;

public class ObserverRelationDAOImpl implements ObserverRelationDAO {
    private final String GET_RELATIONS_BY_OBSERVABLE = "SELECT * FROM observer_relations WHERE user_email = ?";
    private final String INSERT_RELATION = "INSERT INTO observer_relations (user_email, librarian_email) VALUES (?, ?)";
    private final String REMOVE_RELATION = "DELETE FROM observer_relations WHERE user_email = ? AND librarian_email = ?";

    @Autowired
    JdbcTemplate template;

    @Override
    public List<ObserverRelation> getObserverRelationsByObservable(String userEmail) {
        return template.query(GET_RELATIONS_BY_OBSERVABLE, new Object[]{ userEmail },
                              (ResultSetExtractor<List<ObserverRelation>>) rs -> {
            List<ObserverRelation> relations = new ArrayList<>();
            while (rs.next()) {
                relations.add(new ObserverRelation(rs.getString("user_email"),
                                                   rs.getString("librarian_email")));
            }
            return relations;
        });
    }

    @Override
    public List<ObserverRelation> getObserverRelationsByObservable(User user) {
        return getObserverRelationsByObservable(user.getEmail());
    }

    @Override
    public Boolean addObserverRelation(ObserverRelation relation) {
        return template.update(INSERT_RELATION, relation.getUserEmail(), relation.getLibrarianEmail()) == 1;
    }

    @Override
    public Boolean removeObserverRelation(ObserverRelation relation) {
        return template.update(REMOVE_RELATION, relation.getUserEmail(), relation.getLibrarianEmail()) == 1;
    }
}
