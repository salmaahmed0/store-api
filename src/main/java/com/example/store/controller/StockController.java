package com.example.store.controller;

import com.example.store.model.Product;
import com.example.store.model.ProductConsumption;
import com.example.store.model.ResponseValidateProduct;
import com.example.store.model.StockDTO;
import com.example.store.service.StockService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping("/stocks")
    public List<StockDTO> getStocks(){
        return stockService.findAll();
    }

    @PostMapping("/stocks")
    public String  addStock(@RequestBody @Valid StockDTO stockDTO){
        return stockService.save(stockDTO);
    }

    @GetMapping("/stocks/{productCode}")
    public List<StockDTO> searchProduct(@PathVariable String productCode){
        return stockService.findAllByProductCodeContainingIgnoreCase(productCode);
    }

    @PostMapping("/stocks/validate-products")
    public List<ResponseValidateProduct> validateProducts(@RequestBody @Valid List<Product> products){
        return stockService.validateProducts(products);
    }

    @PostMapping("/stocks/consume")
    public String consumeStock(@RequestBody @Valid List<Product> products){
        return stockService.consumeProductsFromStocks(products)? "Products consumed from stock" : "Not valid products";
    }

    @GetMapping("/consumptions")
    public List<ProductConsumption> getConsumptions(){
        return stockService.getProductConsumptions();
    }
}
