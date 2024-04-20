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
        return Stock.builder()
                .creationDate(LocalDate.now())
                .consumedQuantity(0)
                .quantity(stockRequestDTO.getQuantity())
                .productCode(stockRequestDTO.getProductCode())
                .store(storeRepository.findById(stockRequestDTO.getStoreId()).orElse(null))
                .build();
    }

    @Override
    public StockResponseDTO toDTO(Stock stock) {
        return StockResponseDTO.builder()
                .stockId(stock.getId())
                .creationDate(stock.getCreationDate())
                .consumedQuantity(stock.getConsumedQuantity())
                .storeId(stock.getStore().getId())
                .quantity(stock.getQuantity())
                .productCode(stock.getProductCode())
                .build();
    }
}
