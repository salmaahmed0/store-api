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
    public StoreDTO toDTO(Store store) {

        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName(store.getName());
        storeDTO.setCity(store.getCity());
        storeDTO.setPhoneNumber(store.getPhoneNumber());

        return storeDTO;
    }
}
