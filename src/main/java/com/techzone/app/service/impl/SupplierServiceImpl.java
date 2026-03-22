package com.techzone.app.service.impl;

import com.techzone.app.entity.Supplier;
import com.techzone.app.repository.SupplierRepository;
import com.techzone.app.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public List<Supplier> listAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }
}
