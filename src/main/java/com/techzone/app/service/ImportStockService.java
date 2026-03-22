package com.techzone.app.service;

import com.techzone.app.entity.ImportStock;
import com.techzone.app.entity.Product;
import com.techzone.app.entity.AppUser;

import java.util.List;

public interface ImportStockService {
    ImportStock recordImport(Product product, int quantity, AppUser staff);
    List<ImportStock> history();
    List<ImportStock> byProduct(Product product);
}