package com.example.Library.DAO;

import com.example.Library.Model.HelpRequest;

public interface RequestHelpDAO {

    /**
     * add a help request to database 
     * @param requestHelp request of consideration
     * @return if add successfully, return true; else false
     */
    Boolean addRequestHelp(HelpRequest requestHelp);

    /**
     * return the help request
     * @return requestHelp
     */
    HelpRequest getRequest(String email);
}
