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
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public List<StoreResponseDTO> getStores(){
        return storeService.findAll();
    }

    @PostMapping
    public StoreResponseDTO createStore(@RequestBody @Valid StoreRequestDTO storeRequestDTO){
        return storeService.save(storeRequestDTO);
    }

    @GetMapping("/{storeName}")
    public StoreResponseDTO getStoreByName(@PathVariable String storeName){
        return storeService.findByName(storeName);
    }

    @PutMapping
    public StoreResponseDTO updateStore(@RequestBody @Valid StoreResponseDTO storeResponseDTO){
        return storeService.updateStore(storeResponseDTO);
    }

    @DeleteMapping("/{storeName}")
    public void deleteStore(@PathVariable String storeName){
         storeService.deleteStore(storeName);
    }

}
