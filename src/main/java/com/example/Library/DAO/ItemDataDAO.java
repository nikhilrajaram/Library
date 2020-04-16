package com.example.Library.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import com.example.Library.Model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ItemDataDAO implements ItemDataDAOImpl {

    private final String INSERT_ITEMNAME = "INSERT INTO items (ItemId, ItemName) VALUES (?, ?)";
    private final String DELETE_ITEM = "DELETE FROM items WHERE ItemId = ?";


    @Autowired
    JdbcTemplate template;

    public ItemDataDAO() {

    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Boolean addItem(Item item){

        Object[] args = new Object[2];
        args[0] = item.getItemId();
        args[1] = item.getItemName();

        try {
            return template.update(INSERT_ITEMNAME, args) == 1;

        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean removeItem(Item item) {

        Object[] args = {item.getItemId()};

        try {
            return template.update(DELETE_ITEM, args) == 1;

        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean isAvailable(Item item) {

        boolean available = false;

        Object[] args = {item.getItemId()};
        String sql = "SELECT count(*) FROM items WHERE ItemId = ?";

        int count = template.queryForObject(sql, args, Integer.class);

        if (count > 0){
            available = true;
        }

        return available;

    }
}
