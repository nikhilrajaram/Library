package com.example.Library.DAO;

import com.example.Library.Model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("recordDAO")
public class RecordDAOImpl implements RecordDAO {
    private final String INSERT_RECORD = "INSERT INTO records (user_email, item_id, check_out_date, return_by_date) " +
            "VALUES (?, ?, ?, ?)";

    @Autowired
    JdbcTemplate template;

    @Override
    public Boolean insertRecord(Record record) {
        Object[] args = new Object[] { record.getUserEmail(), record.getItemId(),
                record.getCheckOutDate(), record.getReturnByDate() };
        return template.update(INSERT_RECORD, args) == 1;
    }
}
