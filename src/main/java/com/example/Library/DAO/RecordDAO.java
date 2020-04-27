package com.example.Library.DAO;

import com.example.Library.Model.Item;
import com.example.Library.Model.Record;
import com.example.Library.Model.User;

import java.util.List;

public interface RecordDAO {

    /**
     * If user checks out an item, create a record that has User e-mail and Item ID and insert into records database
     * @param record to be created
     * @return if created successfully return true, otherwise return false
     */
    Boolean insertRecord(Record record);

    /**
     * Set a record is_returned field to true when item is returned
     * @param user user to return for
     * @param item item user is returning
     * @return if edited successfully return true, otherwise return false
     */
    Boolean editRecordForReturned(User user, Item item);

    /**
     * Query records for overdue items for user
     * @param user user that may have overdue items
     * @return List of records of overdue items by user
     */
    List<Record> getOverdueItemRecordsByUser(User user);

    /**
     * Query records by item
     * @param item item of consideration
     * @return Record of given item
     */
    Record getRecordByItem(Item item);
}
