package com.example.store.controller;

import com.example.store.model.store.StoreRequestDTO;
import com.example.store.model.store.StoreResponseDTO;
import com.example.store.service.StoreService;
import jakarta.validation.Valid;
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
    public List<StoreResponseDTO> getStores(){
        return storeService.findAll();
    }

    @PostMapping
    public StoreResponseDTO createStore(@RequestBody  StoreRequestDTO storeRequestDTO){
        return storeService.save(storeRequestDTO);
    }

    @GetMapping("/{storeName}")
    public StoreResponseDTO getStoreByName(@PathVariable String storeName){
        return storeService.findByName(storeName);
    }

    @PutMapping
    public StoreResponseDTO updateStore(@RequestBody StoreResponseDTO storeResponseDTO){
        return storeService.updateStore(storeResponseDTO);
    }

    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable long storeId){
         storeService.deleteStore(storeId);
    }

}
