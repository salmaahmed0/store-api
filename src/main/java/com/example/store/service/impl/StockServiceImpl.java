package com.example.store.service.impl;

import com.example.store.entity.Stock;
import com.example.store.entity.Store;
import com.example.store.exception.RecordNotFoundException;
import com.example.store.mapper.StockMapper;
import com.example.store.model.Product;
import com.example.store.model.ProductConsumption;
import com.example.store.model.ResponseValidateProduct;
import com.example.store.model.StockDTO;
import com.example.store.repository.StockRepository;
import com.example.store.service.StockService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired

    private StockRepository stockRepository;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    RestTemplate restTemplate;

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
    public List<ResponseValidateProduct> validateProducts(List<Product> products) {
        List<ResponseValidateProduct> validateProducts = new ArrayList<>() ;

        for(Product product : products){
            ResponseValidateProduct validateProduct = new ResponseValidateProduct();
            List<Stock> stocks = getListOfStocksThatContainsProduct(product);
            if (!stocks.isEmpty()){
                validateProduct.setProductCode(product.getProductCode());
                validateProduct.setValid(true);
            } else {
                validateProduct.setProductCode(product.getProductCode());
                validateProduct.setValid(false);
            }
            validateProducts.add(validateProduct);
        }
        return validateProducts;
    }

    @Override
    public void consumeProductsFromStocks(List<Product> products) {

        List<Stock> validProducts = new ArrayList<>();
        List<Product> invalidProducts = new ArrayList<>();

        for (Product product: products){
            List<Stock> stocks =getListOfStocksThatContainsProduct(product);
            log.info("Stocks: "+ stocks);
            if (!stocks.isEmpty()){
                validProducts.add(stocks.get(0));
            }else {
                invalidProducts.add(product);
            }
        }
        if(invalidProducts.isEmpty()){
            int i=0;
            for (Stock validProduct: validProducts){
                int consumedQuantity =  validProduct.getConsumedQuantity();
                validProduct.setConsumedQuantity(consumedQuantity+products.get(i).getQuantity());
                stockRepository.save(validProduct);
                log.info(validProduct + " consumed");
                i++;
            }
        }else {
            log.warn("Products not valid to consumed" + invalidProducts);
            throw new RecordNotFoundException("Products not valid to consumed" + invalidProducts);
        }
    }

    public List<Stock> getListOfStocksThatContainsProduct(Product product){
        String productCode = product.getProductCode();
        int quantity = product.getQuantity();

        List<Stock> stocks = stockRepository.findAllByProductCodeContainingIgnoreCase(productCode)
                .stream()
                .filter(stock -> stock.getQuantity()-stock.getConsumedQuantity() >= quantity)
                .collect(Collectors.toList());
        log.info("find Stocks By ProductCode: " + productCode + ", And valid Quantity: "+ quantity);
        return stocks;
    }

    public List<Product> getListOfProducts(String resourceUrl){
        ParameterizedTypeReference<List<Product>> typeReference = new ParameterizedTypeReference<List<Product>>() {};
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(
                resourceUrl,
                HttpMethod.GET,
                null,
                typeReference);

        return responseEntity.getBody();
    }

}
