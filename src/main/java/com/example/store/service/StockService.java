package com.example.store.service;

import com.example.store.model.ProductDTO;
import com.example.store.model.ResponseValidateProductDTO;
import com.example.store.model.StockDTO;

import java.util.List;

public interface StockService {

    List<StockDTO> findAll();
    List<StockDTO> findAllByProductCodeContainingIgnoreCase(String productCode);

    String save(StockDTO stockDTO);

    List<ResponseValidateProductDTO> validateProducts(List<ProductDTO> productDTOS);

    Boolean consumeProductsFromStocks(List<ProductDTO> productDTOS);

}
