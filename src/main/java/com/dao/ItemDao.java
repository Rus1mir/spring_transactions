package com.dao;

import com.model.Item;
import javassist.NotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Item save(Item item) {
        entityManager.persist(item);
        return item;
    }

    public Item findById(long id) {
        return entityManager.find(Item.class, id);
    }

    public Item update(Item item) {
        return entityManager.merge(item);
    }

    public void delete(long id) throws Exception {

        Item item = entityManager.find(Item.class, id);

        if (item == null)
            throw new NotFoundException("Item id: " + id + " not found for delete");
        entityManager.remove(item);
    }

    public int deleteByName(String name) {

        Query q = entityManager.createNativeQuery("DELETE FROM ITEMS\n" +
                "WHERE INSTR(NAME, ?) > 0");

        q.setParameter(1, name);
        return q.executeUpdate();
    }
}