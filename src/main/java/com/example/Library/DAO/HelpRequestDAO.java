package com.example.Library.DAO;

import com.example.Library.Model.HelpRequest;
import com.example.Library.Model.User;

import java.util.List;

public interface HelpRequestDAO {

    /**
     * add a help request to database 
     * @param requestHelp request of consideration
     * @return if add successfully, return true; else false
     */
    Boolean addHelpRequest(HelpRequest requestHelp);

    List<HelpRequest> getRequestsForUser(String userEmail);

    List<HelpRequest> getRequestsForUser(User user);

    List<HelpRequest> getRequestsForLibrarian(String librarianEmail);

    List<HelpRequest> getRequestsForLibrarian(User librarian);

}
