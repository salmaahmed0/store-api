package com.example.store.mapper.impl;

import com.example.store.entity.Stock;
import com.example.store.entity.Store;
import com.example.store.mapper.StockMapper;
import com.example.store.model.stock.StockRequestDTO;
import com.example.store.model.stock.StockResponseDTO;
import com.example.store.repository.StoreRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
@Slf4j
public class StockMapperImpl implements StockMapper {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public Stock toEntity(StockRequestDTO stockRequestDTO) {

        Stock stock = new Stock();
        stock.setProductCode(stockRequestDTO.getProductCode());
        stock.setQuantity(stockRequestDTO.getQuantity());
        stock.setConsumedQuantity(0);

        Store store= storeRepository.findById(stockRequestDTO.getStoreId()).orElse(null);
        stock.setStore(store);

        LocalDate date = LocalDate.now();
        stock.setCreationDate(date);

        return stock;
    }

    @Override
    public StockResponseDTO toDTO(Stock stock) {

        StockResponseDTO stockResponseDTO = new StockResponseDTO();
        stockResponseDTO.setStockId(stock.getId());
        stockResponseDTO.setQuantity(stock.getQuantity());
        stockResponseDTO.setProductCode(stock.getProductCode());
        stockResponseDTO.setStoreId(stock.getStore().getId());
        stockResponseDTO.setCreationDate(stock.getCreationDate());
        stockResponseDTO.setConsumedQuantity(stock.getConsumedQuantity());

        return stockResponseDTO;
    }
}
