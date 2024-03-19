package com.example.store.mapper.impl;

import com.example.store.entity.Store;
import com.example.store.mapper.StoreMapper;
import com.example.store.model.StoreDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class StoreMapperImpl implements StoreMapper {


    @Override
    public Store toEntity(StoreDTO storeDTO) {

        Store store = new Store();
        store.setName(storeDTO.getName());
        store.setPhoneNumber(storeDTO.getPhoneNumber());
        store.setCity(storeDTO.getCity());
        store.setStocks(null);

        return store;
    }

    @Override
    public StoreDTO toDTO(Store store) {

        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName(store.getName());
        storeDTO.setCity(store.getCity());
        storeDTO.setPhoneNumber(store.getPhoneNumber());

        return storeDTO;
    }
}
