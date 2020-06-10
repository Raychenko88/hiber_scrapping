package org.example.dao;

import org.example.model.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ItemDAOTest {
    private ItemDAO itemDAO = new ItemDAO();

    private long current_time = new Date().getTime();

    @Test
    void save() {
//        Item item = new Item(itemId, name, url, imageUrl, price, initPrice, availability);

        Item item = new Item("1","test_name","url", "imageUrl", BigDecimal.valueOf(2.5),BigDecimal.valueOf(123), "1");
        itemDAO.save(item);
        item.setUrl("test_code");
        itemDAO.update(item);
        Item itemFromDB = itemDAO.findById(Integer.parseInt(item.getItemId()));
        assertNotNull(itemFromDB);
        assertEquals(item.getUrl(), itemFromDB.getUrl());

        itemDAO.delete(item);
        assertNull(itemDAO.findById(Integer.parseInt(item.getItemId())));
    }
}