package com.example.store.controller;

import com.example.store.entity.Store;
import com.example.store.model.*;
import com.example.store.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class StoreController {

    @Autowired
    StoreService storeService;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/stores")
    public List<StoreDTO> getStores(){
        return storeService.findAll();
    }

    @PostMapping("/stores")
    public String createStore(@RequestBody Store store){
        return storeService.save(store);
    }
    @GetMapping("/consumptions")
    public List<ProductConsumption> getConsumptions(){

        List<ProductConsumption> productConsumptions ;
        // Get this data from product service
        String resourceUrl = "http://localhost:8083/consumptions";
        ParameterizedTypeReference<List<ProductConsumption>> typeReference = new ParameterizedTypeReference<List<ProductConsumption>>() {};
        ResponseEntity<List<ProductConsumption>> responseEntity = restTemplate.exchange(
                resourceUrl,
                HttpMethod.GET,
                null,
                typeReference);

        productConsumptions = responseEntity.getBody();
        log.info("productsConsumptions: " + productConsumptions);
        // I will return it to display in ui.
        return productConsumptions;
    }
}
