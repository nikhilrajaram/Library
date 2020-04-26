package com.example.Library.DAO;

import com.example.Library.Model.Authority;

import java.util.List;

public interface AuthorityDAO {

    /** Get one authority by authority type
     * @param authorityType type of authorization
     * @return  Authority */
    Authority getAuthorityByAuthorityType(String authorityType);

    /** Get n authorities by authority type
     * @param authorityType type of authorization
     * @param n number of authorities to return
     * @return  List of n authorities */
    List<Authority> getnAuthoritiesByAuthorityType(String authorityType, Integer n);

}
