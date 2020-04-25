package com.example.Library.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import com.example.Library.Model.Item;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("itemDataDAO")
public class ItemDataDAOImpl implements ItemDataDAO {

    private final String INSERT_ITEMNAME = "INSERT INTO items (item_id, type, n_available, n_checked_out, " +
            "is_digital) VALUES (?, ?, ?, ?, ?)";
    private final String DELETE_ITEM = "DELETE FROM items WHERE item_id = ?";
    private final String COUNT_ITEM = "SELECT n_available FROM items WHERE item_id = ?";
    private final String GET_ITEM_TYPE = "SELECT * FROM items where item_id = ?";
    private final String UPDATE_N_AVAILABLE = "WITH new_quantities AS (SELECT n_available-1 new_n_available, " +
            "n_checked_out+1 new_n_checked_out, item_id FROM items WHERE item_id = ?)\nUPDATE items SET n_available = " +
            "(SELECT new_n_available from new_quantities), n_checked_out = (SELECT new_n_checked_out from " +
            "new_quantities) WHERE item_id = (SELECT item_id from new_quantities)";


    @Autowired
    JdbcTemplate template;

    public ItemDataDAOImpl() {

    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Boolean addItem(Item item) {
        Object[] args = new Object[5];
        args[0] = item.getItemId();
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
    public List<Boolean> batchAddItems(List<Item> items) {
        List<Boolean> statuses = new ArrayList<>();
        int[] rowsAffected = template.batchUpdate(INSERT_ITEMNAME, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, items.get(i).getItemId());
                ps.setString(2, items.get(i).getType());
                ps.setInt(3, items.get(i).getnAvailable());
                ps.setInt(4, items.get(i).getnCheckedOut());
                ps.setBoolean(5, items.get(i).getDigital());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });

        for (int nAffected : rowsAffected) {
            statuses.add(nAffected == 1);
        }

        return statuses;
    }

    @Override
    public Boolean removeItem(Item item) {
        Object[] args = new Object[1];
        args[0] = item.getItemId();

        try {
            return template.update(DELETE_ITEM, args) == 1;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer getnAvailable(Item item) {
        Object[] args = new Object[1];
        args[0] = item.getItemId();

        return template.query(COUNT_ITEM, args, (ResultSetExtractor<Integer>) rs -> {
            if (!rs.next()) return 0;
            return rs.getInt("n_available");
        });
    }

    @Override
    public Boolean checkOutItem(Item item) {
        return template.update(UPDATE_N_AVAILABLE, item.getItemId()) == 1;
    }

    @Override
    public Item getItemById(Integer itemId) {
        Object[] args = new Object[1];
        args[0] = itemId;
        return template.query(GET_ITEM_TYPE, args, (ResultSetExtractor<Item>) rs -> {
            if (!rs.next()) return null;

            return new Item(rs.getInt("item_id"),
                            rs.getString("type"),
                            rs.getInt("n_available"),
                            rs.getInt("n_checked_out"),
                            rs.getBoolean("is_digital"));
        });
    }

}
