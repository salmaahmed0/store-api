package com.example.store.mapper;

import com.example.store.entity.Store;
import com.example.store.model.StockDTO;
import com.example.store.model.StoreDTO;

public interface StoreMapper {

    StoreDTO toDTO(Store store);

}
