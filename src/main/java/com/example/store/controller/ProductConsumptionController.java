package com.example.store.controller;

import com.example.store.RestTemplateClient;
import com.example.store.model.productConsumption.ProductConsumptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products-history")
public class ProductConsumptionController {
    @Autowired
    RestTemplateClient restTemplateClient;

    @GetMapping
    public List<ProductConsumptionDTO> getAllConsumptions(){
        return restTemplateClient.getProductsConsumptions();
    }

    @GetMapping("/{productCode}")
    public List<ProductConsumptionDTO> getProductConsumptions(@PathVariable String productCode){
        return restTemplateClient.getProductConsumptionsByProductCode(productCode);
    }

    @GetMapping("/by-productCode-and-orderCode")
    public ProductConsumptionDTO getProductConsumptionsInOrder(@RequestParam("productCode") String productCode,
                                                 @RequestParam("orderCode") String orderCode){
        return restTemplateClient.getProductsConsumptionsByProductCodeAndOrderCode(productCode, orderCode);
    }

}
