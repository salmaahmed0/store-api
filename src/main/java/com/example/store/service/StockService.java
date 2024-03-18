package com.example.store.service;

import com.example.store.model.Product;
import com.example.store.model.ProductConsumption;
import com.example.store.model.ResponseValidateProduct;
import com.example.store.model.StockDTO;

import java.util.List;

public interface StockService {

    List<StockDTO> findAll();
    List<StockDTO> findAllByProductCodeContainingIgnoreCase(String productCode);

    String save(StockDTO stockDTO);

    List<ResponseValidateProduct> validateProducts(List<Product> products);

    Boolean consumeProductsFromStocks(List<Product> products);

    List<ProductConsumption> getProductConsumptions();
}
