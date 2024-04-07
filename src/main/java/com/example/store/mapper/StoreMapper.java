package com.example.store.mapper;

import com.example.store.entity.Store;
import com.example.store.model.store.StoreRequestDTO;
import com.example.store.model.store.StoreResponseDTO;

public interface StoreMapper {

    Store toEntity(StoreRequestDTO storeRequestDTO);
    StoreResponseDTO toDTO(Store store);

}
