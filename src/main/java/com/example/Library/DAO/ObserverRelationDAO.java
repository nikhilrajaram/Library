package com.example.Library.DAO;

import com.example.Library.Model.ObserverRelation;
import com.example.Library.Model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObserverRelationDAO {

    /**
     * Query relation between user and librarian
     * @param userEmail user's email of consideration
     * @return List of Observer relation
     */
    List<ObserverRelation> getObserverRelationsByObservable(String userEmail);

    /**
     * Query relation between user and librarian
     * @param user user of consideration
     * @return List of Observer relation
     */
    List<ObserverRelation> getObserverRelationsByObservable(User user);

    /**
     * Add Observer relation to database of observer_relations
     * @param relation relation to be added
     * @return if added successfully return true, otherwise return false
     */
    Boolean addObserverRelation(ObserverRelation relation);

    /**
     * Remove Observer relation from database of observer_relations
     * @param relation to be removed
     * @return if removed successfully return true, otherwise return false
     */
    Boolean removeObserverRelation(ObserverRelation relation);
}
