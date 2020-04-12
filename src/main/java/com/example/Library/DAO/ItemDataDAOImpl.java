package com.example.Library.DAO;

import com.example.Library.Model.Item;

public interface ItemDataDAOImpl {

    /**
     * Add an item to items database
     * @param item to add to database
     * @return if adding is successful return true, else return false
     */
    Boolean addItem(Item item);


    /**
     * Remove an item from items database
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
