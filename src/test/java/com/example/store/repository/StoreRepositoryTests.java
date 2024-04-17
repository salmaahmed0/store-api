package com.example.store.repository;

import com.example.store.entity.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StoreRepositoryTests {
    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void storeRepositoryFindAllReturnAllStores(){
        // Arrange
        Store store1 = Store.builder().name("store1").phoneNumber("01010187876").city("Cairo").build();
        Store store2 = Store.builder().name("store2").phoneNumber("01010187877").city("Cairo").build();
        storeRepository.save(store1);
        storeRepository.save(store2);
        // Act
        List<Store> savedStores = storeRepository.findAll();
        // Assert
        assertThat(savedStores).isNotNull();
        assertThat(savedStores.size()).isEqualTo(2);
    }

    @Test
    public void storeRepositoryFindByNameReturnStore(){
        // Arrange
        Store store1 = Store.builder()
                .name("store1")
                .phoneNumber("01010187876")
                .city("Cairo")
                .build();
        storeRepository.save(store1);
        // Act
        Store store = storeRepository.findByName(store1.getName()).get();
        // Assert
        assertThat(store).isNotNull();
    }

    @Test
    public void storeRepositorySaveReturnSavedStore(){
        // Arrange
        Store store = Store.builder().name("store1").phoneNumber("01010187876").city("Cairo").build();
        // Act
        Store savedStore = storeRepository.save(store);
        // Assert
        assertThat(savedStore).isNotNull();
    }

    @Test
    public void storeRepositoryUpdateReturnSavedStoreIsNotNull(){
        // Arrange
        Store store = Store.builder().name("store1").phoneNumber("01010187876").city("Cairo").build();
        storeRepository.save(store);
        Store savedStore = storeRepository.getById(store.getId());
        savedStore.setName("storeX");
        // Act
        Store updatedStore = storeRepository.save(store);
        // Assert
        assertThat(updatedStore.getName()).isEqualTo("storeX");
    }

    @Test
    public void storeRepositoryDeleteStoreReturnStoreIsEmpty(){
        // Arrange
        Store store = Store.builder().name("store1").phoneNumber("01010187876").city("Cairo").build();
        storeRepository.save(store);
        // Act
        storeRepository.deleteById(store.getId());
        Optional<Store> deletedStore = storeRepository.findById(store.getId());
        // Assert
        assertThat(deletedStore).isEmpty();
    }

    @Test
    public void storeRepositoryFindByIdReturnStore(){
        // Arrange
        Store store1 = Store.builder()
                .name("store1")
                .phoneNumber("01010187876")
                .city("Cairo")
                .build();
        storeRepository.save(store1);
        // Act
        Store store = storeRepository.findById(store1.getId()).get();
        // Assert
        assertThat(store).isNotNull();
    }
}
