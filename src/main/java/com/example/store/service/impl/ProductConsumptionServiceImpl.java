package com.example.store.service.impl;

import com.example.store.model.other.ProductConsumptionDTO;
import com.example.store.service.ProductConsumptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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


}
