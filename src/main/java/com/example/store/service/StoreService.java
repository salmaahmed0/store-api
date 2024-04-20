package com.example.store.service;

import com.example.store.entity.Store;
import com.example.store.model.store.StoreRequestDTO;
import com.example.store.model.store.StoreResponseDTO;

import java.util.List;

public interface StoreService {
    List<StoreResponseDTO> findAll();
    StoreResponseDTO save(StoreRequestDTO storeRequestDTO);
    StoreResponseDTO findByName(String storeName);
    StoreResponseDTO updateStore(StoreResponseDTO storeResponseDTO);
    void deleteStore(String storeName);

}
