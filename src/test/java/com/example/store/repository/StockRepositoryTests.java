package com.example.store.repository;

import com.example.store.entity.Stock;
import com.example.store.entity.Store;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StockRepositoryTests {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    StoreRepository storeRepository;


    @Test
    public void stockRepositoryFindAllAndReturnStocks(){
        // Arrange
        Store store = Store.builder().stocks(Arrays.asList()).name("store").city("cairo").phoneNumber("01010187876").build();
        storeRepository.save(store);
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        Stock stock2 = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        stockRepository.save(stock);
        stockRepository.save(stock2);
        // Act
        List<Stock> savedStocks = stockRepository.findAll();
        // Assert
        assertThat(savedStocks).isNotNull();
        assertThat(savedStocks.size()).isEqualTo(2);
    }

    @Test
    public void stockRepositoryFindByProductCodeReturnStocks(){
        // Arrange
        Store store = Store.builder().stocks(Arrays.asList()).name("store").city("cairo").phoneNumber("01010187876").build();
        storeRepository.save(store);
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        Stock stock2 = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        stockRepository.save(stock);
        stockRepository.save(stock2);
        // Act
        List<Stock> savedStocks = stockRepository.findAllByProductCodeContainingIgnoreCase("2222");
        // Assert
        assertThat(savedStocks).isNotNull();
        assertThat(savedStocks.size()).isEqualTo(2);
    }

    @Test
    public void stockRepositoryFindByIdReturnStock(){
        // Arrange
        Store store = Store.builder().stocks(Arrays.asList()).name("store").city("cairo").phoneNumber("01010187876").build();
        storeRepository.save(store);
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        stockRepository.save(stock);
        // Act
        Stock stock1 = stockRepository.findById(stock.getId()).get();
        // Assert
        assertThat(stock1).isNotNull();
    }

    @Test
    public void stockRepositorySaveReturnSavedStock(){
        // Arrange
        Store store = Store.builder().stocks(Arrays.asList()).name("store").city("cairo").phoneNumber("01010187876").build();
        storeRepository.save(store);
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();

        // Act
        Stock savedStocks = stockRepository.save(stock);
        // Assert
        assertThat(savedStocks).isNotNull();
    }

    @Test
    public void stockRepositoryUpdateReturnSavedStockIsNotNull(){
        // Arrange
        Store store = Store.builder().stocks(Arrays.asList()).name("store").city("cairo").phoneNumber("01010187876").build();
        storeRepository.save(store);
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        stockRepository.save(stock);
        Stock savedStock = stockRepository.findById(stock.getId()).get();
        savedStock.setConsumedQuantity(90);
        // Act
        Stock updatedStock = stockRepository.save(stock);
        // Assert
        assertThat(updatedStock.getConsumedQuantity()).isEqualTo(90);
    }

    @Test
    public void stockRepositoryDeleteStockReturnStoreIsEmpty(){
        // Arrange
        Store store = Store.builder().stocks(Arrays.asList()).name("store").city("cairo").phoneNumber("01010187876").build();
        storeRepository.save(store);
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        stockRepository.save(stock);
        // Act
        stockRepository.deleteById(stock.getId());
        Optional<Stock> deletedStock = stockRepository.findById(stock.getId());
        // Assert
        assertThat(deletedStock).isEmpty();
    }
}
