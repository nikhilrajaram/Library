package com.example.Library.DAO;

import com.example.Library.Model.Authority;

import java.util.List;

public interface AuthorityDAO {

    Authority getAuthorityByAuthorityType(String authorityType);

    List<Authority> getnAuthoritiesByAuthorityType(String authorityType, Integer n);

}
