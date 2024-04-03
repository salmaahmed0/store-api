package com.example.store.controller;

import com.example.store.model.ProductConsumptionDTO;
import com.example.store.service.ProductConsumptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/consumptions")
public class ProductConsumptionController {
    @Autowired
    ProductConsumptionService productConsumptionService;

    @GetMapping
    public List<ProductConsumptionDTO> getAllConsumptions(){
        return productConsumptionService.getAllConsumptions();
    }
    @GetMapping("/{code}")
    public List<ProductConsumptionDTO> getProductConsumptions(@PathVariable String code){
        return productConsumptionService.getProductConsumptions(code);
    }

}
