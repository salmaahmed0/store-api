package com.example.store.service.impl;

import com.example.store.entity.Stock;
import com.example.store.exception.RecordNotFoundException;
import com.example.store.mapper.StockMapper;
import com.example.store.model.other.ValidateProductRequestDTO;
import com.example.store.model.other.ValidateProductResponseDTO;
import com.example.store.model.stock.StockRequestDTO;
import com.example.store.model.stock.StockResponseDTO;
import com.example.store.repository.StockRepository;
import com.example.store.repository.StoreRepository;
import com.example.store.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        List<StockResponseDTO> stockResponseDTOs = stockRepository.findAll()
                .stream()
                .map(stockMapper::toDTO)
                .collect(Collectors.toList());
        if(stockResponseDTOs.isEmpty()){
            log.error("There is no stocks");
            throw new RecordNotFoundException("There is no stocks");
        }
        return stockResponseDTOs;
    }

    @Override
    public List<StockResponseDTO> findAllByProductCodeContainingIgnoreCase(String productCode) {
        List<StockResponseDTO> stockResponseDTOs =  stockRepository.findAllByProductCodeContainingIgnoreCase(productCode)
                .stream()
                .map(stockMapper::toDTO)
                .collect(Collectors.toList());
        if(stockResponseDTOs.isEmpty()){
            log.error("No matched stock with productCode: " + productCode);
            throw new RecordNotFoundException("No matched stock with productCode: " + productCode);
        }
        return stockResponseDTOs;
    }

    @Override
    public String save(StockRequestDTO stockRequestDTO) {

        if(!storeRepository.findById(stockRequestDTO.getStoreId()).isPresent()){
            throw new RecordNotFoundException("You can't add stock in store with id: " + stockRequestDTO.getStoreId() + ", it's not exist!");
        }

        Stock stock = stockMapper.toEntity(stockRequestDTO);
        List<Stock> stocks = stock.getStore().getStocks();
        stockRepository.save(stock);
        stocks.add(stock);
        log.info("stock saved: "+ stock);

        return "New Stock added";
    }

    @Override
    public List<ValidateProductResponseDTO> validateProducts(List<ValidateProductRequestDTO> productDTOS) {
        List<ValidateProductResponseDTO> validateProducts = new ArrayList<>() ;

        for(ValidateProductRequestDTO productDTO : productDTOS){
            ValidateProductResponseDTO validateProduct = new ValidateProductResponseDTO();
            List<Stock> stocks = getListOfStocksThatContainsProduct(productDTO);
            if (!stocks.isEmpty()){
                validateProduct.setProductCode(productDTO.getProductCode());
                validateProduct.setValid(true);
            } else {
                validateProduct.setProductCode(productDTO.getProductCode());
                validateProduct.setValid(false);
            }
            validateProducts.add(validateProduct);
        }
        return validateProducts;
    }

    @Override
    public Boolean consumeProductsFromStocks(List<ValidateProductRequestDTO> productDTOS) {

        List<Stock> validProducts = new ArrayList<>();
        List<ValidateProductRequestDTO> invalidProductDTOS = new ArrayList<>();

        for (ValidateProductRequestDTO productDTO : productDTOS){
            List<Stock> stocks =getListOfStocksThatContainsProduct(productDTO);
            log.info("Stocks: "+ stocks);
            if (!stocks.isEmpty()){
                validProducts.add(stocks.get(0));
            }else {
                invalidProductDTOS.add(productDTO);
            }
        }
        if(invalidProductDTOS.isEmpty()){
            int i=0;
            for (Stock validProduct: validProducts){
                int consumedQuantity =  validProduct.getConsumedQuantity();
                validProduct.setConsumedQuantity(consumedQuantity+ productDTOS.get(i).getQuantity());
                stockRepository.save(validProduct);
                log.info(validProduct + " consumed");
                i++;
            }
        }else {
            log.warn("Products not valid to consumed" + invalidProductDTOS);
            throw new RecordNotFoundException("Products not valid to consumed" + invalidProductDTOS);
        }
        return true;
    }


    public List<Stock> getListOfStocksThatContainsProduct(ValidateProductRequestDTO productDTO){
        String productCode = productDTO.getProductCode();
        int quantity = productDTO.getQuantity();

        List<Stock> stocks = stockRepository.findAllByProductCodeContainingIgnoreCase(productCode)
                .stream()
                .filter(stock -> stock.getQuantity()-stock.getConsumedQuantity() >= quantity)
                .collect(Collectors.toList());
        log.info("find Stocks By ProductCode: " + productCode + ", And Quantity: "+ quantity);
        return stocks;
    }



}
