package com.example.store.controller;

import com.example.store.model.productConsumption.ProductConsumptionDTO;
import com.example.store.model.productConsumption.ProductOrderDTO;
import com.example.store.service.ProductConsumptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/product-order")
    public ProductConsumptionDTO getProductOrder(@RequestBody ProductOrderDTO productOrderDTO){
        return productConsumptionService.getProductInOrder(productOrderDTO);
    }

    @GetMapping("/order-product")
    public void deleteProductConsumptionRecord(@RequestBody ProductOrderDTO productOrderDTO){
        productConsumptionService.deleteRecord(productOrderDTO);
    }
}
