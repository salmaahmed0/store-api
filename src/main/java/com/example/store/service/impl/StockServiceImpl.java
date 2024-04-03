package com.example.store.service.impl;

import com.example.store.entity.Stock;
import com.example.store.exception.RecordNotFoundException;
import com.example.store.mapper.StockMapper;
import com.example.store.model.ProductDTO;
import com.example.store.model.ResponseValidateProductDTO;
import com.example.store.model.StockDTO;
import com.example.store.repository.StockRepository;
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

    @Override
    public List<StockDTO> findAll() {
        List<StockDTO> stockDTOS = stockRepository.findAll()
                .stream()
                .map(stockMapper::toDTO)
                .collect(Collectors.toList());
        if(stockDTOS.isEmpty()){
            log.error("There is no stocks");
            throw new RecordNotFoundException("There is no stocks");
        }
        return stockDTOS;
    }

    @Override
    public List<StockDTO> findAllByProductCodeContainingIgnoreCase(String productCode) {
        List<StockDTO> stockDTOS =  stockRepository.findAllByProductCodeContainingIgnoreCase(productCode)
                .stream()
                .map(stockMapper::toDTO)
                .collect(Collectors.toList());
        if(stockDTOS.isEmpty()){
            log.error("No matched stock with productCode: " + productCode);
            throw new RecordNotFoundException("No matched stock with productCode: " + productCode);
        }
        return stockDTOS;
    }

    @Override
    public String save(StockDTO stockDTO) {

        Stock stock = stockMapper.toEntity(stockDTO);
        List<Stock> stocks = stock.getStore().getStocks();
        stockRepository.save(stock);
        stocks.add(stock);
        log.info("stock saved: "+ stock);

        return "New Stock added";
    }

    @Override
    public List<ResponseValidateProductDTO> validateProducts(List<ProductDTO> productDTOS) {
        List<ResponseValidateProductDTO> validateProducts = new ArrayList<>() ;

        for(ProductDTO productDTO : productDTOS){
            ResponseValidateProductDTO validateProduct = new ResponseValidateProductDTO();
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
    public Boolean consumeProductsFromStocks(List<ProductDTO> productDTOS) {

        List<Stock> validProducts = new ArrayList<>();
        List<ProductDTO> invalidProductDTOS = new ArrayList<>();

        for (ProductDTO productDTO : productDTOS){
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


    public List<Stock> getListOfStocksThatContainsProduct(ProductDTO productDTO){
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
