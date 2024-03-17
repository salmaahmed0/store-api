package com.example.store.mapper;

import com.example.store.entity.Stock;
import com.example.store.model.StockDTO;

public interface StockMapper {
    Stock toEntity(StockDTO stockDTO);
    StockDTO toDTO(Stock stock);
}
