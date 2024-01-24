package com.example.crudapp.controller;

import com.example.crudapp.service.ItemService;
import com.example.crudapp.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/getAllItems")
    public ResponseEntity<List<Item>> getAllItems(@RequestHeader("Auth") String authToken) {
        itemService.validateAuthToken(authToken);

        List<Item> itemList = itemService.findAllItems();

        if (itemList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @GetMapping("/getItem/{type}")
    public ResponseEntity<List<Item>> getItemByType(@PathVariable String type, @RequestHeader("Auth") String authToken) {
        itemService.validateAuthToken(authToken);

        List<Item> itemList = itemService.findItemByType(type);

        if (itemList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> addItem(@RequestBody Item item, @RequestHeader("Auth") String authToken) {
        itemService.validateAuthToken(authToken);

        Item newItem = itemService.saveItem(item);

        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id, @RequestHeader("Auth") String authToken) {
        itemService.validateAuthToken(authToken);

        itemService.deleteItemById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
