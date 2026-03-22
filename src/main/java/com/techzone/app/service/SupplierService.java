package com.techzone.app.service;

import com.techzone.app.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> listAll();
    Supplier getById(Long id);
    Supplier save(Supplier supplier);
    void delete(Long id);
}
