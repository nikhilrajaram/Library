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

    /**
     * Query help requests for user
     * @param userEmail user's email associated with help requests
     * @return List of help requests for user with userEmail
     */
    List<HelpRequest> getRequestsForUser(String userEmail);

    /**
     * Query help requests for user
     * @param user user associated with help requests
     * @return List of help requests for user
     */
    List<HelpRequest> getRequestsForUser(User user);

    /**
     * Query help requests for librarian
     * @param librarianEmail librarian's email associated with help requests
     * @return List of help requests for librarian with librarianEmail
     */
    List<HelpRequest> getRequestsForLibrarian(String librarianEmail);

    /**
     * Query help requests for librarian
     * @param librarian librarian associated with help requests
     * @return List of help requests for librarian
     */
    List<HelpRequest> getRequestsForLibrarian(User librarian);

}
