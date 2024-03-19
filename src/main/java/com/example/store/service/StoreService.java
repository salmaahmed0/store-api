package com.example.store.service;

import com.example.store.entity.Store;
import com.example.store.model.StoreDTO;

import java.util.List;

public interface StoreService {
    List<StoreDTO> findAll();
    String save(StoreDTO storeDTO);
}
