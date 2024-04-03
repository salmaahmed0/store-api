package com.example.store.controller;

import com.example.store.model.ProductDTO;
import com.example.store.model.ResponseValidateProductDTO;
import com.example.store.model.StockDTO;
import com.example.store.service.StockService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/stocks")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping
    public List<StockDTO> getStocks(){
        return stockService.findAll();
    }

    @PostMapping
    public String  addStock(@RequestBody @Valid StockDTO stockDTO){
        return stockService.save(stockDTO);
    }

    @GetMapping("/{productCode}")
    public List<StockDTO> searchProduct(@PathVariable String productCode){
        return stockService.findAllByProductCodeContainingIgnoreCase(productCode);
    }

    @PostMapping("/validate-products")
    public List<ResponseValidateProductDTO> validateProducts(@RequestBody @Valid List<ProductDTO> productDTOS){
        return stockService.validateProducts(productDTOS);
    }

    @PostMapping("/consume")
    public String consumeStock(@RequestBody @Valid List<ProductDTO> productDTOS){
        return stockService.consumeProductsFromStocks(productDTOS)? "Products consumed from stock" : "Not valid products";
    }


}
