package com.example.Library.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import com.example.Library.Model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class ItemDataDAO implements ItemDataDAOImpl {

    private final String INSERT_ITEMNAME = "INSERT INTO items (item_id, type, n_available, n_checked_out, " +
            "is_digital) VALUES (?, ?, ?, ?, ?)";
    private final String DELETE_ITEM = "DELETE FROM items WHERE item_id = ?";
    private final String COUNT_ITEM = "SELECT n_available FROM items WHERE item_id = ?";


    @Autowired
    JdbcTemplate template;

    public ItemDataDAO() {

    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Boolean addItem(Item item){
        Object[] args = new Object[5];
        args[0] = item.getId();
        args[1] = item.getType();
        args[2] = item.getnAvailable();
        args[3] = item.getnCheckedOut();
        args[4] = item.getDigital();

        try {
            return template.update(INSERT_ITEMNAME, args) == 1;

        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean removeItem(Item item) {
        Object[] args = new Object[1];
        args[0] = item.getId();

        try {
            return template.update(DELETE_ITEM, args) == 1;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean isAvailable(Item item) {
        Object[] args = new Object[1];
        args[0] = item.getId();

        return template.query(COUNT_ITEM, args, (ResultSetExtractor<Boolean>) rs -> {
            if (!rs.next()) return false;

            return rs.getInt("n_available") > 0;
        });
    }
}
