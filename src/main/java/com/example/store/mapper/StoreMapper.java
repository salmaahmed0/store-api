package com.example.store.mapper;

import com.example.store.entity.Store;
import com.example.store.model.StockDTO;
import com.example.store.model.StoreDTO;

public interface StoreMapper {

    Store toEntity(StoreDTO storeDTO);
    StoreDTO toDTO(Store store);

}
