package com.example.Library.DAO;

import com.example.Library.Model.RequestHelp;

public interface RequestHelpDAO {

    /**
     * add a help request to database 
     * @param requestHelp request of consideration
     * @return if add successfully, return true; else false
     */
    Boolean addRequestHelp(RequestHelp requestHelp);

    /**
     * return the help request
     * @return requestHelp
     */
    RequestHelp getRequest(String email);
}
