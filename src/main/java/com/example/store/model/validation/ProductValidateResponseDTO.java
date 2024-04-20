package com.example.store.model.validation;

import lombok.Data;

@Data
public class ProductValidateResponseDTO {
    private String productCode;
    private boolean valid;
}
