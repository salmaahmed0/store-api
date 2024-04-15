package com.example.store.controller;

import com.example.store.model.validation.ValidateProductRequestDTO;
import com.example.store.model.validation.ValidateProductResponseDTO;
import com.example.store.model.stock.StockRequestDTO;
import com.example.store.model.stock.StockResponseDTO;
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
    public List<StockResponseDTO> getStocks(){
        return stockService.findAll();
    }

    @PostMapping
    public StockResponseDTO  addStock(@RequestBody @Valid StockRequestDTO stockRequestDTO){
        return stockService.save(stockRequestDTO);
    }

    @PutMapping
    public StockResponseDTO updateStock(@RequestBody StockResponseDTO stockResponseDTO){
        return stockService.updateStock(stockResponseDTO);
    }

    @GetMapping("/{productCode}")
    public List<StockResponseDTO> searchProduct(@PathVariable String productCode){
        return stockService.findAllByProductCodeContainingIgnoreCase(productCode);
    }

    @DeleteMapping("/{stockId}")
    public void deleteStock(@PathVariable long stockId){
        stockService.deleteStock(stockId);
    }

    @PostMapping("/validate-products")
    public List<ValidateProductResponseDTO> validateProducts(@RequestBody @Valid List<ValidateProductRequestDTO> productDTOS){
        return stockService.validateProducts(productDTOS);
    }

    @PostMapping("/consume")
    public String consumeStock(@RequestBody @Valid List<ValidateProductRequestDTO> productDTOS){
        return stockService.consumeProductsFromStocks(productDTOS)? "Products consumed from stock" : "Not valid products";
    }


}
