package com.example.store.controller;

import com.example.store.model.validation.ProductValidationRequestDTO;
import com.example.store.model.validation.ProductValidateResponseDTO;
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
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping
    public List<StockResponseDTO> getStocks(){
        return stockService.findAll();
    }

    @PostMapping
    public StockResponseDTO addStock(@RequestBody @Valid StockRequestDTO stockRequestDTO){
        return stockService.createNewStock(stockRequestDTO);
    }

    @PutMapping
    public StockResponseDTO updateStock(@RequestBody @Valid StockResponseDTO stockResponseDTO){
        return stockService.updateStock(stockResponseDTO);
    }

    @GetMapping("/{productCode}")
    public List<StockResponseDTO> searchProduct(@PathVariable String productCode){
        return stockService.findStocksByProductCode(productCode);
    }

    @DeleteMapping("/{stockId}")
    public void deleteStock(@PathVariable long stockId){
        stockService.deleteStock(stockId);
    }

    @PostMapping("/validate-products")
    public List<ProductValidateResponseDTO> validateProducts(@RequestBody @Valid List<ProductValidationRequestDTO> productDTOS){
        return stockService.validateProducts(productDTOS);
    }

    @PostMapping("/consume-products")
    public String consumeStock(@RequestBody @Valid List<ProductValidationRequestDTO> productDTOS){
        log.info("in controller");
        return stockService.checkProductsValidationAndConsume(productDTOS);
    }


}
