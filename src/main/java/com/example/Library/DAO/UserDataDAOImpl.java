package com.example.Library.DAO;

import com.example.Library.Model.User;

import javax.sql.DataSource;

public interface UserDataDAOImpl {
    /**
     * Query user database and check if an email is already registered
     * @param email email address of consideration
     * @return if already registered, return false; else true
     */
    Boolean isEmailAvailable(String email);

    /**
     * Fetches a new UID for registering a new user
     * @return UID
     */
    Integer getNewUid();

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
