package com.example.store.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StoreDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;
    @NotBlank(message = "City is mandatory")
    private String city;
}
