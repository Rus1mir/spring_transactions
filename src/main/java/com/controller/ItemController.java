package com.controller;

import com.service.ItemService;
import com.model.Item;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/item")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Item> save(@RequestBody Item item) {

        return new ResponseEntity<>(itemService.save(item), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find")
    public ResponseEntity<Item> findById(@RequestParam(value = "id") Long id) throws Exception {

        Item item = itemService.findById(id);
        if (item == null)
            throw new NotFoundException("Item id: " + id + " was not found");
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    public ResponseEntity<Item> update(@RequestBody Item item) throws Exception {

        if (itemService.findById(item.getId()) == null)
            throw new NotFoundException("Item id: " + item.getId() + " was not found");

        return new ResponseEntity<>(itemService.update(item), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) throws Exception {

        itemService.delete(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete_by_name")
    public ResponseEntity<String> delete(@RequestParam(value = "name") String name) {

        int count = itemService.deleteByName(name);
        return new ResponseEntity<>(count + " items deleted", HttpStatus.OK);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> error(DataIntegrityViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> error(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> error(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
