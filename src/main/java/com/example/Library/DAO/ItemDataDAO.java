package com.example.Library.DAO;

import com.example.Library.Model.Item;

import java.util.List;

public interface ItemDataDAO {

    /**
     * Add an item to items table
     * @param item to add to database
     * @return if adding is successful return true, else return false
     */
    Boolean addItem(Item item);

    /**
     * Add items to items table in batches
     * @param items list of items to add
     * @return list of statuses for each item
     */
    List<Boolean> batchAddItems(List<Item> items);

    /**
     * Remove an item from items table
     * @param item to to remove
     * @return if removing is successful return true, else return false
     */
    Boolean removeItem(Item item);

    /**
     * Query item database and if item is available
     * @param item item to be checked
     * @return if item is available, return true; else false
     */
    Boolean isAvailable(Item item);

}
