package com.example.store.model.other;

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
