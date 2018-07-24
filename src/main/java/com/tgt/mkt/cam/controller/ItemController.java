package com.tgt.mkt.cam.controller;

import com.tgt.mkt.cam.entity.Item;
import com.tgt.mkt.cam.repository.ItemRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/item")
    @ApiOperation(value = "REST endpoint to fetch Project and its details",
            notes = "REST endpoint to fetch Project and its details",
            response = Item.class)
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @Cacheable(value = "item", key = "#itemId")
    @GetMapping("/item/{item_id}")
    public Optional<Item> fetchItem(@PathVariable("item_id") Long itemId) {
        log.info("Fetching Result from Database");
        return itemRepository.findById(itemId);
    }
}
