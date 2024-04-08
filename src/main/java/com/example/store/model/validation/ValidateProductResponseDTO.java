package com.example.store.model.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateProductResponseDTO {
    private String productCode;
    private boolean valid;
}
