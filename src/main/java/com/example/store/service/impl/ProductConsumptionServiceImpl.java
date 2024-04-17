package com.example.store.service.impl;

import com.example.store.model.productConsumption.ProductConsumptionDTO;
import com.example.store.model.productConsumption.ProductOrderDTO;
import com.example.store.service.ProductConsumptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@Slf4j
public class ProductConsumptionServiceImpl implements ProductConsumptionService {

    @Autowired
    RestTemplate restTemplate;
    @Override
    public List<ProductConsumptionDTO> getAllConsumptions() {
        List<ProductConsumptionDTO> productConsumptionDTOS;
        String resourceUrl = "http://localhost:8083/api/products/history";
        ParameterizedTypeReference<List<ProductConsumptionDTO>> typeReference = new ParameterizedTypeReference<List<ProductConsumptionDTO>>() {};
        ResponseEntity<List<ProductConsumptionDTO>> responseEntity = restTemplate.exchange(
                resourceUrl,
                HttpMethod.GET,
                null,
                typeReference);

        productConsumptionDTOS = responseEntity.getBody();
        log.info("productsConsumptions: " + productConsumptionDTOS);
        return productConsumptionDTOS;
    }

    @Override
    public List<ProductConsumptionDTO> getProductConsumptions(String code) {

        String resourceUrl = "http://localhost:8083/api/products/history/" + code;
        ParameterizedTypeReference<List<ProductConsumptionDTO>> typeReference = new ParameterizedTypeReference<List<ProductConsumptionDTO>>() {};
        ResponseEntity<List<ProductConsumptionDTO>> responseEntity = restTemplate.exchange(
                resourceUrl,
                HttpMethod.GET,
                null,
                typeReference);
        List<ProductConsumptionDTO> productConsumptionDTOs = responseEntity.getBody();
        log.info("product Consumptions: " + productConsumptionDTOs);
        return productConsumptionDTOs;
    }

    @Override
    public ProductConsumptionDTO getProductInOrder(String productCode, String orderCode) {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO(productCode, orderCode);
        log.info("productOrderDTO: "+ productOrderDTO);
        String resourceUrl = "http://localhost:8083/api/products/history/product-order";
        HttpEntity<ProductOrderDTO> request = new HttpEntity<>(productOrderDTO);
        ResponseEntity<ProductConsumptionDTO> responseEntity = restTemplate.exchange(
                resourceUrl,
                HttpMethod.POST,
                request,
                ProductConsumptionDTO.class);
        log.info("productConsumption: " + responseEntity.getBody());
        return responseEntity.getBody();
    }

    @Override
    public void deleteRecord(ProductOrderDTO productOrderDTO) {
        restTemplate.exchange(
                "http://localhost:8083/api/products/history/product-order",
                HttpMethod.DELETE,
                new HttpEntity<>(productOrderDTO),
                ProductConsumptionDTO.class);
    }


}
