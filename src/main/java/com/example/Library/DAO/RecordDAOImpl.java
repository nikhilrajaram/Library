package com.example.Library.DAO;

import com.example.Library.Model.Item;
import com.example.Library.Model.Record;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("recordDAO")
public class RecordDAOImpl implements RecordDAO {
    private final String INSERT_RECORD = "INSERT INTO records (user_email, item_id, check_out_date, return_by_date) " +
            "VALUES (?, ?, ?, ?)";
    private final String GET_OVERDUE_RECORDS = "SELECT * FROM records WHERE NOW() > return_by_date and user_email = ?";
    private final String GET_ITEM = "SELECT * FROM records WHERE item_id = ?";

    @Autowired
    JdbcTemplate template;

    @Override
    public Boolean insertRecord(Record record) {
        Object[] args = new Object[] { record.getUserEmail(), record.getItemId(),
                record.getCheckOutDate(), record.getReturnByDate() };
        return template.update(INSERT_RECORD, args) == 1;
    }

    @Override
    public List<Record> getOverdueItemRecordsByUser(User user) {
        return template.query(GET_OVERDUE_RECORDS, new Object[] { user.getEmail() },
                (ResultSetExtractor<List<Record>>) rs -> {
            List<Record> records = new ArrayList<>();
            while (rs.next()) {
                records.add(new Record(rs.getString("user_email"),
                                       rs.getInt("item_id"),
                                       rs.getDate("check_out_date"),
                                       rs.getDate("return_by_date")));
            }
            return records;
        });
    }

    @Override
    public Record getRecordByItem(Item item) {
        return template.query(GET_ITEM, new Object[] { item.getItemId() }, (ResultSetExtractor<Record>) rs -> {
            if (!rs.next()) return null;

            return new Record(rs.getString("user_email"),
                              rs.getInt("item_id"),
                              rs.getDate("check_out_date"),
                              rs.getDate("return_by_date"));
        });
    }
}
