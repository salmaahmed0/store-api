package com.example.store.model.store;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreRequestDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 11, max = 11, message = "Phone number must contains 11 numeric characters")
    @Pattern(regexp = "\\d{11}", message = "Phone number must contains 11 numeric characters")
    private String phoneNumber;
    @NotBlank(message = "City is mandatory")
    private String city;
}
