package com.techzone.app.repository;

import com.techzone.app.entity.ImportStock;
import com.techzone.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportStockRepository extends JpaRepository<ImportStock, Long> {
    List<ImportStock> findByProduct(Product product);
}