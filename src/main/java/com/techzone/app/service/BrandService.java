package com.techzone.app.service;

import com.techzone.app.entity.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> listAll();
    Brand getById(Long id);
    Brand save(Brand brand);
    void delete(Long id);
}
