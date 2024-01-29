package com.example.crudapp;


import com.example.crudapp.model.Item;
import com.example.crudapp.repo.ItemRepository;
import com.example.crudapp.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class itemServicesTest {

    @Autowired
    private ItemService service;

    @MockBean
    private ItemRepository repository;

    @Test
    public void findAllItemsTest() {
        when(repository.findAll()).thenReturn(Stream
                .of(new Item(1L, "XYZ", 2, "ABC"), new Item(2L, "QWE", 3, "JKL"))
                .collect(Collectors.toList()));

        assertEquals(2, service.findAllItems().size());
    }

    @Test
    public void findAllItemsByTypeTest() {
        String type = "ABC";
        when(repository.findByType(type)).thenReturn(Stream
                .of(new Item(1L, "XYZ", 2, "ABC"))
                .collect(Collectors.toList()));

        assertEquals(1, service.findItemByType(type).size());
    }


    @Test
    public void saveItemTest() {
        Item item = new Item(16L, "ASDF", 62, "GHJK");
        when(repository.save(item)).thenReturn(item);
        assertEquals(item, service.saveItem(item));
    }

    @Test
    public void deleteItemByIdTest() {
        Item item = new Item(16L, "ASDF", 62, "GHJK");
        service.deleteItemById(16L);
        verify(repository, times(1)).delete(item);

    }
}
