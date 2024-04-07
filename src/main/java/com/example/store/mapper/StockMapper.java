package com.example.store.mapper;

import com.example.store.entity.Stock;
import com.example.store.model.stock.StockRequestDTO;
import com.example.store.model.stock.StockResponseDTO;

public interface StockMapper {
    Stock toEntity(StockRequestDTO stockRequestDTO);
    StockResponseDTO toDTO(Stock stock);
}
