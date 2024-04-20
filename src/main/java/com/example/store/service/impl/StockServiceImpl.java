package com.example.store.service.impl;

import com.example.store.entity.Stock;
import com.example.store.entity.Store;
import com.example.store.exception.DataNotValidException;
import com.example.store.exception.RecordNotFoundException;
import com.example.store.mapper.StockMapper;
import com.example.store.model.validation.ProductValidationRequestDTO;
import com.example.store.model.validation.ProductValidateResponseDTO;
import com.example.store.model.stock.StockRequestDTO;
import com.example.store.model.stock.StockResponseDTO;
import com.example.store.repository.StockRepository;
import com.example.store.repository.StoreRepository;
import com.example.store.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    StoreRepository storeRepository;

    @Override
    public List<StockResponseDTO> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(stockMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockResponseDTO> findStocksByProductCode(String productCode) {
        return stockRepository.findStockByProductCodeContainingIgnoreCase(productCode)
                .stream()
                .map(stockMapper::toDTO)
                .toList();
    }

    @Override
    public StockResponseDTO createNewStock(StockRequestDTO stockRequestDTO) {

        if(storeRepository.findById(stockRequestDTO.getStoreId()).isPresent()){
            throw new RecordNotFoundException("Store with id " + stockRequestDTO.getStoreId() + " not exist, you can't add on it!");
        }

        Stock stock = stockMapper.toEntity(stockRequestDTO);
        List<Stock> stocks = stock.getStore().getStocks();
        stock = stockRepository.save(stock);
        stocks.add(stock);
        log.info("stock {} saved", stock);
        return stockMapper.toDTO(stock);
    }

    @Override
    public StockResponseDTO updateStock(StockResponseDTO stockResponseDTO) {
        Stock stock = stockRepository.findById(stockResponseDTO.getStockId())
                .orElseThrow(()-> new RecordNotFoundException("You can't update stock not exist with id: "+ stockResponseDTO.getStockId()));
        stock.setProductCode(stockResponseDTO.getProductCode());
        stock.setQuantity(stockResponseDTO.getQuantity());
        stock.setConsumedQuantity(stockResponseDTO.getConsumedQuantity());
        stock.setCreationDate(stockResponseDTO.getCreationDate());

        Store store = storeRepository.findById(stockResponseDTO.getStoreId())
                .orElseThrow(()-> new RecordNotFoundException("Store with id: " +
                        stockResponseDTO.getStoreId() +
                        " Not found, you can't add stock on it"));
        stock.setStore(store);
        stock = stockRepository.save(stock);
        return stockMapper.toDTO(stock);
    }

    @Override
    public void deleteStock(long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException("Stock with id: " + id + " not found, you can't delete it!"));
        stockRepository.delete(stock);
    }

    @Override
    public List<ProductValidateResponseDTO> validateProducts(List<ProductValidationRequestDTO> productDTOS) {
        List<ProductValidateResponseDTO> validProducts = new ArrayList<>() ;

        for(ProductValidationRequestDTO productDTO : productDTOS){
            ProductValidateResponseDTO validProduct = new ProductValidateResponseDTO();
            List<Stock> stocks = getValidListOfStocksThatContainsProduct(productDTO);
            if (!stocks.isEmpty()){
                validProduct.setProductCode(productDTO.getProductCode());
                validProduct.setValid(true);
            } else {
                validProduct.setProductCode(productDTO.getProductCode());
                validProduct.setValid(false);
            }
            validProducts.add(validProduct);
        }
        return validProducts;
    }

    @Override
    public String checkProductsValidationAndConsume(List<ProductValidationRequestDTO> productDTOS) {
        Map<Stock, Integer> stocks = new HashMap<>();
        for (ProductValidationRequestDTO productDTO : productDTOS){
            Optional<Stock> optionalStock =getValidListOfStocksThatContainsProduct(productDTO)
                    .stream()
                    .findFirst();
            optionalStock.ifPresentOrElse(
                    stock -> {
                        stocks.put(stock, productDTO.getQuantity());
                        log.info("{} is valid for productCode {} and quantity {}",
                                stock, productDTO.getProductCode(), productDTO.getQuantity());
                    },
                    ()->{
                        log.error("No stock valid for {}, no stocks consumed for any product in the list", productDTO);
                        throw new DataNotValidException("No stock valid for productCode" + productDTO.getProductCode()+
                                " with quantity " +productDTO +
                                ", no stock consumed for any product in the list"
                        );
                    }
                    );
        }
        return consumeFromStocks(stocks);
    }

    private String consumeFromStocks(Map<Stock, Integer> stocks) {
        for (Map.Entry<Stock, Integer> entry : stocks.entrySet()){
            entry.getKey().setConsumedQuantity(
                    entry.getValue() + entry.getKey().getConsumedQuantity()
            );
            stockRepository.save(entry.getKey());
        }
        return "Products consumed from stock";
    }

    private List<Stock> getValidListOfStocksThatContainsProduct(ProductValidationRequestDTO productDTO){
        String productCode = productDTO.getProductCode();
        int quantity = productDTO.getQuantity();

        return stockRepository.findStockByProductCodeContainingIgnoreCase(productCode)
                .stream()
                .filter(stock -> stock.getQuantity()-stock.getConsumedQuantity() >= quantity)
                .collect(Collectors.toList());
    }



}
