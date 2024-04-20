package com.example.store.service;

import com.example.store.entity.Stock;
import com.example.store.entity.Store;
import com.example.store.exception.RecordNotFoundException;
import com.example.store.mapper.StockMapper;
import com.example.store.model.stock.StockRequestDTO;
import com.example.store.model.stock.StockResponseDTO;
import com.example.store.model.validation.ProductValidationRequestDTO;
import com.example.store.model.validation.ProductValidateResponseDTO;
import com.example.store.repository.StockRepository;
import com.example.store.repository.StoreRepository;
import com.example.store.service.impl.StockServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class StockServiceTests {
    @Mock
    private StockRepository stockRepository;
    @Mock
    private StockMapper stockMapper;
    @Mock
    private StoreRepository storeRepository;
    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    public void stockServiceSaveShouldReturnStockResponseDTO() {
        // Arrange
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        StockResponseDTO stockResponse = StockResponseDTO.builder().stockId(stock.getId()).storeId(store.getId())
                .productCode(stock.getProductCode()).quantity(stock.getQuantity())
                .consumedQuantity(stock.getConsumedQuantity())
                .creationDate(stock.getCreationDate()).build();

        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));
        when(stockMapper.toEntity(any(StockRequestDTO.class))).thenReturn(stock);
        when(stockRepository.save(stock)).thenReturn(stock);
        when(stockMapper.toDTO(stock)).thenReturn(stockResponse);

        // Act
        StockResponseDTO savedStockResponse = stockService.createNewStock(StockRequestDTO.builder()
                .quantity(100).productCode("2222").storeId(store.getId()).build());

        // Assert
        assertThat(savedStockResponse).isEqualTo(stockResponse);
    }

    @Test
    public void stockServiceSaveShouldThrowExceptionIfStoreNotPresent(){
        //Arrange
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        when(storeRepository.findById(store.getId())).thenReturn(Optional.empty());
        // Act
        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> stockService.createNewStock(
                        StockRequestDTO.builder().productCode("2222").quantity(10).storeId(1L).build()
                ));
        // Assert
        assertThat(ex.getMessage()).isEqualTo(
                "You can't add stock in store with id: " + store.getId() + ", it's not exist!"
        );
    }

    @Test
    public void stockServiceFindAllShouldReturnStockResponseDTOs(){
        // Arrange
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        when(stockRepository.findAll()).thenReturn(Arrays.asList(
                Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10).quantity(100).productCode("2222").store(store).build(),
                Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10).quantity(100).productCode("2222").store(store).build()
        ));
        // Act
        List<StockResponseDTO> stocks = stockService.findAll();
        // assert
        assertThat(stocks.size()).isEqualTo(2);
    }

    @Test
    public void stockServiceFindAllThrowExceptionIfNoStocksExist() {
        // Arrange
        when(stockRepository.findAll()).thenReturn(Collections.emptyList());
        // Act
        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> stockService.findAll()
        );
        // Assert
        assertThat(ex.getMessage()).isEqualTo("There is no stocks");
    }

    @Test
    public void stockServiceFindByProductCodeReturnStoreResponseDTOs(){
        // Arrange
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        when(stockRepository.findStockByProductCodeContainingIgnoreCase("2222")).thenReturn(Arrays.asList(
                Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10).quantity(100).productCode("2222").store(store).build(),
                Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10).quantity(100).productCode("2222").store(store).build()
        ));
        // Act
        List<StockResponseDTO> stocks = stockService.findStocksByProductCode("2222");
        // assert
        assertThat(stocks.size()).isEqualTo(2);
    }

    @Test
    public void stockServiceFindByProductCodeThrowExceptionIfNoMatches(){
        // Arrange
        when(stockRepository.findStockByProductCodeContainingIgnoreCase("2222")).thenReturn(Collections.emptyList());
        // Act
        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> stockService.findStocksByProductCode("2222")
        );
        // Assert
        assertThat(ex.getMessage()).isEqualTo("No matched stock with productCode: 2222");
    }

    @Test
    public void stockServiceUpdateStockReturnUpdatedStock(){
        // Arrange
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        StockResponseDTO stockResponse = StockResponseDTO.builder().stockId(stock.getId()).storeId(store.getId())
                .productCode(stock.getProductCode()).quantity(stock.getQuantity())
                .consumedQuantity(stock.getConsumedQuantity())
                .creationDate(stock.getCreationDate()).build();

        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
        when(stockRepository.save(stock)).thenReturn(stock);
        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));
        when(stockMapper.toDTO(stock)).thenReturn(stockResponse);

        // Act
        StockResponseDTO savedStockResponse = stockService.updateStock(stockResponse);
        // Assert
        assertThat(savedStockResponse).isEqualTo(stockResponse);
    }

    @Test
    public void stockServiceUpdateStockThrowExceptionWhenStockNotFound(){
        // Arrange
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        StockResponseDTO stockResponse = StockResponseDTO.builder().stockId(stock.getId()).storeId(store.getId())
                .productCode(stock.getProductCode()).quantity(stock.getQuantity())
                .consumedQuantity(stock.getConsumedQuantity())
                .creationDate(stock.getCreationDate()).build();

        when(stockRepository.findById(stock.getId())).thenReturn(Optional.empty());

        // Act
        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> stockService.updateStock(stockResponse)
        );
        // Assert
        assertThat(ex.getMessage()).isEqualTo("You can't update stock not exist with id: "+ stockResponse.getStockId());
    }

    @Test
    public void stockServiceUpdateStockThrowExceptionWhenStoreNotFound(){
        // Arrange
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        StockResponseDTO stockResponse = StockResponseDTO.builder().stockId(stock.getId()).storeId(store.getId())
                .productCode(stock.getProductCode()).quantity(stock.getQuantity())
                .consumedQuantity(stock.getConsumedQuantity())
                .creationDate(stock.getCreationDate()).build();

        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
        when(storeRepository.findById(store.getId())).thenReturn(Optional.empty());

        // Act
        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> stockService.updateStock(stockResponse)
        );
        // Assert
        assertThat(ex.getMessage()).isEqualTo("Store with id: " +
                stockResponse.getStoreId() +
                " Not found, you can't add stock on it");
    }

    @Test
    public void stockServiceDeleteStockDeletedSuccessfully() {
        // Arrange
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
        doNothing().when(stockRepository).delete(stock);
        // Act
        stockService.deleteStock(stock.getId());
        // Assert
        verify(stockRepository).delete(stock);
    }

    @Test
    public void stockServiceDeleteThrowExceptionIfStockNotFound() {
        // Arrange
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.empty());
        // Act
        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> stockService.deleteStock(stock.getId())
        );
        // Assert
        assertThat(ex.getMessage()).isEqualTo("Stock with id: " + stock.getId() + " not found, you can't delete it!");
    }

    @Test
    public void validateProductsAndReturnValidateProductResponseDTO(){
        ProductValidationRequestDTO validateProductRequest = ProductValidationRequestDTO.builder()
                .productCode("2222")
                .quantity(2).build();
        ProductValidationRequestDTO validateProductRequest2 = ProductValidationRequestDTO.builder()
                .productCode("1234")
                .quantity(2).build();
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        Stock stock2 = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();

        when(stockService.getListOfStocksThatContainsProduct(validateProductRequest)).thenReturn(Arrays.asList(
                stock, stock2
        ));

        // Act
        List<ProductValidateResponseDTO> responseDTOS = stockService.validateProducts(Arrays.asList(
                validateProductRequest, validateProductRequest2
        ));
        // assert
        assertThat(responseDTOS.size()).isEqualTo(2);

    }

    @Test
    public void validateProductsWhenStocksIsEmpty(){
        ProductValidationRequestDTO validateProductRequest = ProductValidationRequestDTO.builder()
                .productCode("2222")
                .quantity(2).build();
        ProductValidationRequestDTO validateProductRequest2 = ProductValidationRequestDTO.builder()
                .productCode("1234")
                .quantity(2).build();
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        Stock stock2 = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();

        when(stockService.getListOfStocksThatContainsProduct(validateProductRequest)).thenReturn(Collections.emptyList());

        // Act
        List<ProductValidateResponseDTO> responseDTOS = stockService.validateProducts(Arrays.asList(
                validateProductRequest, validateProductRequest2
        ));
        // assert
        assertThat(responseDTOS.size()).isEqualTo(2);

    }

    @Test
    public void getListOfStocksThatContainsProduct(){
        Store store = Store.builder().id(1L)
                .stocks(new ArrayList<>()).name("store").city("cairo").phoneNumber("01010187876").build();
        Stock stock = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();
        Stock stock2 = Stock.builder().creationDate(LocalDate.now()).consumedQuantity(10)
                .quantity(100).productCode("2222").store(store).build();

        when(stockRepository.findStockByProductCodeContainingIgnoreCase("2222")).thenReturn(Arrays.asList(stock, stock2));

        // Act
        List<Stock> stocks = stockService.getListOfStocksThatContainsProduct(
                ProductValidationRequestDTO.builder().quantity(10).productCode("2222").build()
        );
        // Assert
        assertThat(stocks.size()).isEqualTo(2);
    }

}
