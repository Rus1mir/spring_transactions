package com.service;


import com.dao.ItemDao;
import com.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ItemService {

    private ItemDao itemDao;

    @Autowired
    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public Item save(Item item) {

        return itemDao.save(item);
    }

    public Item update(Item item) {

        return itemDao.update(item);
    }

    public Item findById(Long id) {

        return itemDao.findById(id);
    }

    public void delete(Long id) throws Exception {

        itemDao.delete(id);
    }

    public int deleteByName(String name) {

        return itemDao.deleteByName(name);
    }
}
