package com.tgt.mkt.cam.controller;

import com.tgt.mkt.cam.entity.Item;
import com.tgt.mkt.cam.repository.ItemRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/item")
    @ApiOperation(value = "REST endpoint to fetch Project and its details",
            notes = "REST endpoint to fetch Project and its details",
            response = Item.class)
    public Item createItem(@RequestBody Item item) {
        System.out.println(item.toString());
        return itemRepository.save(item);
    }

    @GetMapping("/item")
    public Iterable<Item> fetchItem() {

        return itemRepository.findAll();
    }
}
