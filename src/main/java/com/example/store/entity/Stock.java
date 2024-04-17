package com.example.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Stock {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(name = "product_code", nullable = false)
    private String productCode;
    @ManyToOne
    @JoinColumn(name = "store_id",nullable = false)
    private Store store;
    @Column(nullable = false)
    private int quantity;
    @Column(name= "consumed_quantity")
    private int consumedQuantity;
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", quantity=" + quantity +
                ", consumedQuantity"+consumedQuantity+
                ", creationDate=" + creationDate +
                '}';
    }
}
