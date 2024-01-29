package com.example.crudapp;

import com.example.crudapp.controller.ItemController;
import com.example.crudapp.service.ItemService;
import com.example.crudapp.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class itemControllerTest {

    @MockBean
    private ItemService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllItemsTest() throws Exception {
        List<Item> itemList = Arrays.asList(new Item(1L, "XYZ", 2, "ABC"), new Item(2L, "QWE", 3, "JKL"));

        when(service.findAllItems()).thenReturn(itemList);
        mockMvc.perform(get("/api/getAllItems")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("XYZ"));
    }


    @Test
    public void getItemByTypeTest() throws Exception {
        List<Item> itemList = Arrays.asList(new Item(1L, "XYZ", 2, "ABC"), new Item(10L, "ghr", 6, "ABC"));
        when(service.findItemByType("ABC")).thenReturn(itemList);

        mockMvc.perform(get("/api/getItem/{type}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name").value("ghr"));
    }


    @Test
    public void addItemTest() throws Exception{
        Item item = new Item(35L, "BMW", 1, "Car");
        when(service.saveItem(item)).thenReturn(item);
        mockMvc.perform(post("/api/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(item)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteItemByIdTest() throws Exception {
        Item item = new Item(35L, "BMW", 1, "Car");
        doNothing().when(service).deleteItemById(35L);

        mockMvc.perform(delete("/api/deleteItem/{id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(item)))
                .andExpect(status().isOk());
    }
}
