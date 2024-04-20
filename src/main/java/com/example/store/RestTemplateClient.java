package com.example.store;

import com.example.store.model.productConsumption.ProductConsumptionDTO;
import com.example.store.model.productConsumption.ProductOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.store.WebConfig.PRODUCT_CONSUMPTIONS_URL;

@Component
@Slf4j
public class RestTemplateClient {

    private final RestTemplate restTemplate;

    public RestTemplateClient(RestTemplateBuilder builder){
        this.restTemplate = builder.build();
    }

    public List<ProductConsumptionDTO> getProductsConsumptions() {
        return getResponseBody(PRODUCT_CONSUMPTIONS_URL);
    }

    public List<ProductConsumptionDTO> getProductConsumptionsByProductCode(String productCode) {
        return getResponseBody(PRODUCT_CONSUMPTIONS_URL + "/" +  productCode);
    }


    public ProductConsumptionDTO getProductsConsumptionsByProductCodeAndOrderCode(String productCode, String orderCode) {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO(productCode, orderCode);
        HttpEntity<ProductOrderDTO> request = new HttpEntity<>(productOrderDTO);
        return restTemplate.exchange(
                PRODUCT_CONSUMPTIONS_URL + "/product-order",
                HttpMethod.POST,
                request,
                ProductConsumptionDTO.class
                ).getBody();
    }

    private List<ProductConsumptionDTO> getResponseBody(String productConsumptionsUrl) {
        ParameterizedTypeReference<List<ProductConsumptionDTO>> typeReference = new ParameterizedTypeReference<List<ProductConsumptionDTO>>() {};
        ResponseEntity<List<ProductConsumptionDTO>> responseEntity = restTemplate.exchange(
                productConsumptionsUrl,
                HttpMethod.GET,
                null,
                typeReference);

        return responseEntity.getBody();
    }

}
