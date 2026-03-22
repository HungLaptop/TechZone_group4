package com.techzone.app.service;

import com.techzone.app.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> listAll();
    List<Product> search(String query, Long categoryId, Long brandId, Long supplierId, Double minPrice, Double maxPrice, String sort);
    Product getById(Long id);
    Product save(Product product);
    void delete(Long id);
}
