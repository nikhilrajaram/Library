package com.example.Library.DAO;

import com.example.Library.Model.Librarian;
import com.example.Library.Model.User;

public interface LibrarianDataDAO {
    /**
     * Query librarian database and check if an email is already registered
     * @param email email address of consideration
     * @return if already registered, return false; else true
     */
    Boolean isEmailAvailable(String email);

    /**
     * Query librarian database and return the corresponding librarian object
     * @param email email of user of consideration
     * @return serialized librarian object corresponding to email query
     */
    Librarian getLibrarian(String email);

    /**
     * Set librarian LID and add information to librarian database
     * @param librarian librarian to add to database
     * @return status of registration process (success or failure)
     */
    Boolean registerLibrarian(Librarian librarian);

    /**
     * Get bcrypt encrypted password to librarian
     * @param librarian librarian credentials to check
     * @return according librarian bcrypt
     */
    String getBcryptPassword(Librarian librarian);
}
