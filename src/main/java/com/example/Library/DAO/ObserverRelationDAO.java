package com.example.Library.DAO;

import com.example.Library.Model.ObserverRelation;
import com.example.Library.Model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObserverRelationDAO {

    List<ObserverRelation> getObserverRelationsByObservable(String userEmail);

    List<ObserverRelation> getObserverRelationsByObservable(User user);

    Boolean addObserverRelation(ObserverRelation relation);

    Boolean removeObserverRelation(ObserverRelation relation);
}
