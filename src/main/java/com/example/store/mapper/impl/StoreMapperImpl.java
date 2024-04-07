package com.example.store.mapper.impl;

import com.example.store.entity.Store;
import com.example.store.mapper.StoreMapper;
import com.example.store.model.store.StoreRequestDTO;
import com.example.store.model.store.StoreResponseDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class StoreMapperImpl implements StoreMapper {


    @Override
    public Store toEntity(StoreRequestDTO storeRequestDTO) {

        Store store = new Store();
        store.setName(storeRequestDTO.getName());
        store.setPhoneNumber(storeRequestDTO.getPhoneNumber());
        store.setCity(storeRequestDTO.getCity());
        store.setStocks(null);

        return store;
    }

    @Override
    public StoreResponseDTO toDTO(Store store) {

        StoreResponseDTO storeResponseDTO = new StoreResponseDTO();
        storeResponseDTO.setId(store.getId());
        storeResponseDTO.setName(store.getName());
        storeResponseDTO.setCity(store.getCity());
        storeResponseDTO.setPhoneNumber(store.getPhoneNumber());

        return storeResponseDTO;
    }
}
