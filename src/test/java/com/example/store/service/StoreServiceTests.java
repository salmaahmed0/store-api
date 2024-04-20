package com.example.store.service;

import com.example.store.entity.Store;
import com.example.store.exception.ConflictException;
import com.example.store.exception.RecordNotFoundException;
import com.example.store.mapper.StoreMapper;
import com.example.store.model.store.StoreRequestDTO;
import com.example.store.model.store.StoreResponseDTO;
import com.example.store.repository.StoreRepository;
import com.example.store.service.impl.StoreServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@Slf4j
public class StoreServiceTests {
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private StoreMapper storeMapper;
    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    public void storeServiceSaveShouldReturnStoreResponseDTO() {
        // Arrange
        Store store = Store.builder().name("store").city("Cairo").phoneNumber("01010187876").build();
        StoreResponseDTO storeResponse = StoreResponseDTO.builder().name("store").city("Cairo").phoneNumber("01010187876").build();
        StoreRequestDTO storeRequest = StoreRequestDTO.builder().name("store").city("Cairo").phoneNumber("01010187876").build();

        when(storeMapper.toEntity(storeRequest)).thenReturn(store);
        when(storeRepository.save(any(Store.class))).thenReturn(store);
        when(storeMapper.toDTO(store)).thenReturn(storeResponse);
        // Act
        StoreResponseDTO savedStore = storeService.save(storeRequest);

        // Assert
        assertThat(savedStore).isEqualTo(storeResponse);
    }

    @Test
    public void storeServiceSaveShouldThrowExceptionIfNameIsExist() {
        // Arrange
        StoreRequestDTO storeRequestDTO = StoreRequestDTO.builder()
                .name("store")
                .city("Cairo")
                .phoneNumber("01010187876")
                .build();

        when(storeRepository.findByName(storeRequestDTO.getName()))
                .thenReturn(Optional.of(
                        Store.builder()
                                .name("store").city("Cairo").phoneNumber("01010187878").build()
                        )
                );

        // Act
        ConflictException ex = assertThrows(
                ConflictException.class,
                () -> storeService.save(storeRequestDTO)
        );
        // assert
        assertThat(ex.getMessage()).isEqualTo("This store name is already exist!");
    }

    @Test
    public void storeServiceSaveShouldThrowExceptionIfPhoneNumberIsExist() {
        // Arrange
        StoreRequestDTO storeRequestDTO = StoreRequestDTO.builder()
                .name("store")
                .city("Cairo")
                .phoneNumber("01010187876")
                .build();

        when(storeRepository.findByName(storeRequestDTO.getName()))
                .thenReturn(Optional.of(
                                Store.builder()
                                        .name("storeX").city("Cairo").phoneNumber("01010187876")
                                        .build()
                        )
                );

        // Act
        ConflictException ex = assertThrows(
                ConflictException.class,
                () -> storeService.save(storeRequestDTO)
        );
        // Assert
        assertThat(ex.getMessage()).isEqualTo("This store name is already exist!");
    }

    @Test
    public void storeServiceFindAllReturnStoresResponseDTO() {
        // Arrange
        when(storeRepository.findAll()).thenReturn(Arrays.asList(
                Store.builder().name("store").city("Cairo").phoneNumber("01010187878").build(),
                Store.builder().name("store2").city("Cairo").phoneNumber("01010187876").build()
        ));
        // Act
        List<StoreResponseDTO> stores = storeService.findAll();
        // assert
        assertThat(stores.size()).isEqualTo(2);
    }

    @Test
    public void storeServiceFindAllThrowExceptionIfNoStoresExist() {
        // Arrange
        when(storeRepository.findAll()).thenReturn(Collections.emptyList());
        // Act
        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> storeService.findAll()
        );
        // Assert
        assertThat(ex.getMessage()).isEqualTo("There is no stores exist!");
    }

    @Test
    public void storeServiceFindByNameReturnStoreResponseDTO() {
        // Arrange
        Store store = Store.builder().name("store").city("Cairo").phoneNumber("01010187878").build();
        when(storeRepository.findByName("store")).thenReturn(Optional.of(store));
        when(storeMapper.toDTO(store)).thenReturn(
                StoreResponseDTO.builder().name("store").city("Cairo").phoneNumber("01010187878").build()
        );
        // Act
        StoreResponseDTO storeResponse = storeService.findByName("store");
        // Assert
        assertThat(storeResponse).isNotNull();
        assertThat(storeResponse.getName()).isEqualTo("store");
    }

    @Test
    public void storeServiceFindByNameThrowExceptionIfStoreNotFound() {
        // Arrange
        when(storeRepository.findByName("store")).thenReturn(Optional.empty());
        // Act
        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> storeService.findByName("store")
        );
        // Assert
        assertThat(ex.getMessage()).isEqualTo("Store with name store Not FOUND!");
    }

    @Test
    public void storeServiceDeleteStoreDeletedSuccessfully() {
        // Arrange
        Store store = Store.builder().name("store").city("Cairo").phoneNumber("01010187878").build();
        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));
        doNothing().when(storeRepository).delete(store);
        // Act
        storeService.deleteStore(store.getName());
        // Assert
        verify(storeRepository).delete(store);
    }

    @Test
    public void storeServiceDeleteThrowExceptionIfStoreNotFound() {
        // Arrange
        Store store = Store.builder().name("store").city("Cairo").phoneNumber("01010187878").build();
        when(storeRepository.findById(store.getId())).thenReturn(Optional.empty());
        // Act
        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> storeService.deleteStore(store.getName())
        );
        // Assert
        assertThat(ex.getMessage()).isEqualTo("You can't delete not exist store with id: " + store.getId());
    }

    @Test
    public void storeServiceUpdateStoreReturnUpdatedStore() {
        // Arrange
        Store store = Store.builder().name("store").city("Cairo").phoneNumber("01010187878").build();
        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));
        store.setName("updatedStore");
        when(storeRepository.save(store)).thenReturn(store);
        when(storeMapper.toDTO(store)).thenReturn(
                StoreResponseDTO.builder().name("updatedStore").city("Cairo").phoneNumber("01010187878").build()
        );

        // Act
        StoreResponseDTO storeResponse = storeService.updateStore(
                StoreResponseDTO.builder().name("store").city("Cairo").phoneNumber("01010187878").build()
        );

        // Assert
        assertThat(storeResponse.getName()).isEqualTo("updatedStore");
    }

    @Test
    public void storeServiceUpdateStoreThrowRecordNotFound() {
        // Arrange
        StoreResponseDTO store = StoreResponseDTO.builder().name("store").city("Cairo").phoneNumber("01010187878").build();
        when(storeRepository.findById(store.getId())).thenReturn(Optional.empty());

        // Act
        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> storeService.updateStore(store)
        );
        // Assert
        assertThat(ex.getMessage()).isEqualTo("You can't update not exist store with id: " + store.getId());
    }


    @Test
    public void storeServiceUpdateStoreReturnExceptionIfStoreNameExistBefore() {
        // Arrange
        StoreResponseDTO storeResponse = StoreResponseDTO.builder().name("store").city("Cairo").phoneNumber("01010187878").build();
        Store existingStore = Store.builder().name("existingStore").city("Cairo").phoneNumber("02020202020").build();
        when(storeRepository.findById(storeResponse.getId())).thenReturn(Optional.of(existingStore));
        when(storeRepository.findByName(storeResponse.getName())).thenReturn(Optional.of(existingStore));  // another store saved with the sme name
        // Act
        ConflictException ex = assertThrows(
                ConflictException.class,
                () -> storeService.updateStore(storeResponse)
        );
        // assert
        assertThat(ex.getMessage()).isEqualTo("This store name is already exist!");
    }

    @Test
    public void storeServiceUpdateStoreReturnExceptionIfPhoneNumberAssignedToAnotherStore() {
        // Arrange
        StoreResponseDTO storeResponse = StoreResponseDTO.builder().name("store").city("Cairo").phoneNumber("01010187878").build();
        Store existingStore = Store.builder().name("existing store").city("Cairo").phoneNumber("02020202020").build();
        when(storeRepository.findById(storeResponse.getId())).thenReturn(Optional.of(existingStore));
        when(storeRepository.findByName(storeResponse.getName())).thenReturn(Optional.empty());
        when(storeRepository.findByPhoneNumber(storeResponse.getPhoneNumber())).thenReturn(Optional.of(existingStore));  // another store saved with the sme name
        // Act
        ConflictException ex = assertThrows(
                ConflictException.class,
                () -> storeService.updateStore(storeResponse)
        );
        // assert
        assertThat(ex.getMessage()).isEqualTo("This PhoneNumber is already represented to another store!!");
    }

}