package com.example.Library.DAO;

import com.example.Library.Model.User;

public interface UserDataDAO {
    /**
     * Query user database and check if an email is already registered
     * @param email email address of consideration
     * @return if already registered, return false; else true
     */
    Boolean isEmailAvailable(String email);

    /**
     * Query user database and return the corresponding user object
     * @param email email of user of consideration
     * @return serialized user object corresponding to email query
     */
    User getUser(String email);

    /**
     * Set user UID and add information to user database
     * @param user user to add to database
     * @return status of registration process (success or failure)
     */
    Boolean registerUser(User user);

    /**
     * Get bcrypt encrypted password to user
     * @param user user credentials to check
     * @return according user bcrypt
     */
    String getBcryptPassword(User user);
}
