package com.example.store.mapper.impl;

import com.example.store.entity.Stock;
import com.example.store.entity.Store;
import com.example.store.mapper.StockMapper;
import com.example.store.model.StockDTO;
import com.example.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    public Stock toEntity(StockDTO stockDTO) {

        Stock stock = new Stock();
        stock.setProductCode(stockDTO.getProductCode());
        stock.setQuantity(stockDTO.getQuantity());
        stock.setConsumedQuantity(0);

        Store store= storeRepository.findById(stockDTO.getStoreId()).orElse(null);
        stock.setStore(store);

        LocalDate date = LocalDate.now();
        stock.setCreationDate(date);

        return stock;
    }

    @Override
    public StockDTO toDTO(Stock stock) {

        StockDTO stockDTO = new StockDTO();
        stockDTO.setQuantity(stock.getQuantity());
        stockDTO.setProductCode(stock.getProductCode());
        stockDTO.setStoreId(stock.getStore().getId());
        stockDTO.setCreationDate(stock.getCreationDate());
        stockDTO.setConsumedQuantity(stock.getConsumedQuantity());

        return stockDTO;
    }
}
