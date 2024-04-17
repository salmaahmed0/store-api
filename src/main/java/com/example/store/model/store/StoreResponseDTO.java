package com.example.store.model.store;

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
    private String name;
    private String phoneNumber;
    private String city;
}
