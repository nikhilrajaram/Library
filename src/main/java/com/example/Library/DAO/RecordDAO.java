package com.example.Library.DAO;

import com.example.Library.Model.Item;
import com.example.Library.Model.Record;
import com.example.Library.Model.User;

import java.util.List;

public interface RecordDAO {
    Boolean insertRecord(Record record);

    List<Record> getOverdueItemRecordsByUser(User user);

    Record getRecordByItem(Item item);
}
