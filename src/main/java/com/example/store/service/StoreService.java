package com.example.store.service;

import com.example.store.model.store.StoreRequestDTO;
import com.example.store.model.store.StoreResponseDTO;

import java.util.List;

public interface StoreService {
    List<StoreResponseDTO> findAll();
    String save(StoreRequestDTO storeRequestDTO);
    StoreResponseDTO findByName(String storeName);
    String updateStore(StoreResponseDTO storeResponseDTO);
    String deleteStore(long id);

}
