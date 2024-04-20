package com.example.store.model.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponseDTO {
    private long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 11, max = 11, message = "Phone number must contains 11 numeric characters")
    @Pattern(regexp = "\\d{11}", message = "Phone number must contains 11 numeric characters")
    private String phoneNumber;
    @NotBlank(message = "City is mandatory")
    private String city;
}
