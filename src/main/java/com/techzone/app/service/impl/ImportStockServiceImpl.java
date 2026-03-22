package com.techzone.app.service.impl;

import com.techzone.app.entity.AppUser;
import com.techzone.app.entity.ImportStock;
import com.techzone.app.entity.Product;
import com.techzone.app.repository.ImportStockRepository;
import com.techzone.app.service.ImportStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportStockServiceImpl implements ImportStockService {

    private final ImportStockRepository importStockRepository;

    @Override
    public ImportStock recordImport(Product product, int quantity, AppUser staff) {
        ImportStock record = ImportStock.builder()
                .product(product)
                .quantity(quantity)
                .staff(staff)
                .importedAt(LocalDateTime.now())
                .build();
        return importStockRepository.save(record);
    }

    @Override
    public List<ImportStock> history() {
        return importStockRepository.findAll();
    }

    @Override
    public List<ImportStock> byProduct(Product product) {
        return importStockRepository.findByProduct(product);
    }
}
