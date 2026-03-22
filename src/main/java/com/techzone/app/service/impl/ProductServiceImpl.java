package com.techzone.app.service.impl;

import com.techzone.app.entity.Product;
import com.techzone.app.repository.ProductRepository;
import com.techzone.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> search(String query, Long categoryId, Long brandId, Long supplierId, Double minPrice, Double maxPrice, String sort) {
        List<Product> products = productRepository.findAll();

        if (query != null && !query.isBlank()) {
            products = productRepository.findByNameContainingIgnoreCase(query);
        }

        if (categoryId != null) {
            products = products.stream().filter(p -> p.getCategory() != null && p.getCategory().getId().equals(categoryId)).toList();
        }
        if (brandId != null) {
            products = products.stream().filter(p -> p.getBrand() != null && p.getBrand().getId().equals(brandId)).toList();
        }
        if (supplierId != null) {
            products = products.stream().filter(p -> p.getSupplier() != null && p.getSupplier().getId().equals(supplierId)).toList();
        }
        if (minPrice != null) {
            products = products.stream().filter(p -> p.getPrice() >= minPrice).toList();
        }
        if (maxPrice != null) {
            products = products.stream().filter(p -> p.getPrice() <= maxPrice).toList();
        }
        if (sort != null) {
            return switch (sort.toLowerCase()) {
                case "price-asc" -> products.stream().sorted((a, b) -> Double.compare(a.getPrice(), b.getPrice())).toList();
                case "price-desc" -> products.stream().sorted((a, b) -> Double.compare(b.getPrice(), a.getPrice())).toList();
                case "stock" -> products.stream().sorted((a, b) -> Integer.compare(b.getStock(), a.getStock())).toList();
                default -> products;
            };
        }
        return products;
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
