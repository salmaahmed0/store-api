package com.example.store.controller;

import com.example.store.model.Product;
import com.example.store.model.ProductConsumption;
import com.example.store.model.ResponseValidateProduct;
import com.example.store.model.StockDTO;
import com.example.store.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping
    public List<StockDTO> getStocks(){
        return stockService.findAll();
    }

    @PostMapping
    public String  addStock(@RequestBody StockDTO stockDTO){
        return stockService.save(stockDTO);
    }

    @GetMapping("/{productCode}")
    public List<StockDTO> searchProduct(@PathVariable String productCode){
        return stockService.findAllByProductCodeContainingIgnoreCase(productCode);
    }

    @GetMapping("/validate-products")
    public List<ResponseValidateProduct> validateProducts(){

        String resourceUrl = "http://localhost:8083/stocks/validate-products";
        List<Product> products = stockService.getListOfProducts(resourceUrl);
        List<ResponseValidateProduct> validateProducts = stockService.validateProducts(products);
        log.info("Valid Products : " + validateProducts);

        return validateProducts;
    }

    @GetMapping("/consume")
    public String consumeStock(){
        String resourceUrl = "http://localhost:8083/stocks/consume-products";
        List<Product> products = stockService.getListOfProducts(resourceUrl);
        stockService.consumeProductsFromStocks(products);
        log.info("Product consumed from stock");
        return "Product consumed from stock";
    }


}
