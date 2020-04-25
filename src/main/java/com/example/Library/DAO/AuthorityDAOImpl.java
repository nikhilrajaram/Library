package com.example.Library.DAO;

import com.example.Library.Model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("authorityDAO")
public class AuthorityDAOImpl implements AuthorityDAO {
    private final String GET_AUTHORITY = "SELECT * FROM authorities WHERE authority = ? ORDER BY RANDOM() LIMIT 1";
    private final String GET_N_AUTHORITIES = "SELECT * FROM authorities WHERE authority = ? ORDER BY RANDOM() LIMIT ?";

    @Autowired
    JdbcTemplate template;

    @Override
    public Authority getAuthorityByAuthorityType(String authorityType) {
        return template.query(GET_AUTHORITY, new Object[]{ authorityType }, (ResultSetExtractor<Authority>) rs -> {
            if (!rs.next()) return null;

            return new Authority(rs.getString("email"),
                                 rs.getString("authority"));
        });
    }

    @Override
    public List<Authority> getnAuthoritiesByAuthorityType(String authorityType, Integer n) {
        Object[] args = new Object[2];
        args[0] = authorityType;
        args[1] = n;

        return template.query(GET_N_AUTHORITIES, args, (ResultSetExtractor<List<Authority>>) rs -> {
            List<Authority> authorities = new ArrayList<>();
            while (rs.next()) {
                authorities.add(new Authority(rs.getString("email"),
                                              rs.getString("authority")
                ));
            }
            return authorities;
        });
    }
}
