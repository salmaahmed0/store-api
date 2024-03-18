package com.example.store.controller;

import com.example.store.entity.Store;
import com.example.store.model.*;
import com.example.store.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    StoreService storeService;

    @GetMapping
    public List<StoreDTO> getStores(){
        return storeService.findAll();
    }

    @PostMapping
    public String createStore(@RequestBody Store store){
        return storeService.save(store);
    }

}
