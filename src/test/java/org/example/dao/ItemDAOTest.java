package org.example.dao;

import org.example.model.ItemModel;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ItemDAOTest {
    private ItemDAO itemDAO = new ItemDAO();

    private long current_time = new Date().getTime();

    @Test
    void save() {
        ItemModel item = new ItemModel(1,"test_name", "t_code", 123, 1);
        itemDAO.save(item);
        item.setCode("test_code");
        itemDAO.update(item);
        ItemModel itemFromDB = itemDAO.findById(item.getId());
        assertNotNull(itemFromDB);
        assertEquals(item.getCode(), itemFromDB.getCode());

        itemDAO.delete(item);
        assertNull(itemDAO.findById(item.getId()));
    }
}