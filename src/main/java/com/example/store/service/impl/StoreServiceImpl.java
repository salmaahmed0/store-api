package com.example.store.service.impl;

import com.example.store.entity.Store;
import com.example.store.exception.ConflictException;
import com.example.store.exception.RecordNotFoundException;
import com.example.store.mapper.StoreMapper;
import com.example.store.model.store.StoreRequestDTO;
import com.example.store.model.store.StoreResponseDTO;
import com.example.store.repository.StoreRepository;
import com.example.store.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    StoreMapper storeMapper;

    @Override
    public List<StoreResponseDTO> findAll() {

        List<StoreResponseDTO> storeResponseDTOS = storeRepository.findAll()
                .stream()
                .map(storeMapper::toDTO)
                .collect(Collectors.toList());

        if(storeResponseDTOS.isEmpty()){
            log.error("There is no stores exist!");
            throw new RecordNotFoundException("There is no stores exist!");
        }
        return storeResponseDTOS;
    }

    @Override
    public String save(StoreRequestDTO storeRequestDTO) {

        if(storeRepository.findByName(storeRequestDTO.getName()).isPresent()){
            log.error("This store is already exist!");
            throw new ConflictException("This store is already exist!");
        }
        if(storeRepository.findByPhoneNumber(storeRequestDTO.getPhoneNumber()).isPresent()){
            log.error("This PhoneNumber is already represented to another store!");
            throw new ConflictException("This PhoneNumber is already represented to another store!!");
        }
        Store store = storeMapper.toEntity(storeRequestDTO);
        storeRepository.save(store);
        log.info("New Store Created!");
        return "New Store Created!";
    }

    @Override
    public StoreResponseDTO findByName(String storeName) {
        Store store = storeRepository.findByName(storeName)
                .orElseThrow(() -> new RecordNotFoundException("Store with name " + storeName + " Not FOUND!"));

        return storeMapper.toDTO(store);
    }
}
