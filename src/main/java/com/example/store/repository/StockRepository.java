package com.example.store.repository;

import com.example.store.entity.Stock;
import com.example.store.model.StockDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByProductCodeContainingIgnoreCase(String productCode);

}
