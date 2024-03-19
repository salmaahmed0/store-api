package com.example.store.service.impl;

import com.example.store.entity.Store;
import com.example.store.exception.ConflictException;
import com.example.store.exception.RecordNotFoundException;
import com.example.store.mapper.StoreMapper;
import com.example.store.model.StoreDTO;
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
    public List<StoreDTO> findAll() {

        List<StoreDTO> storeDTOS = storeRepository.findAll()
                .stream()
                .map(storeMapper::toDTO)
                .collect(Collectors.toList());

        if(storeDTOS.isEmpty()){
            log.error("There is no stores exist!");
            throw new RecordNotFoundException("There is no stores exist!");
        }
        return storeDTOS;
    }

    @Override
    public String save(StoreDTO storeDTO) {

        if(storeRepository.findByName(storeDTO.getName()).isPresent()){
            log.error("This store is already exist!");
            throw new ConflictException("This store is already exist!");
        }
        Store store = storeMapper.toEntity(storeDTO);
        storeRepository.save(store);
        log.info("New Store Created!");
        return "New Store Created!";
    }
}
