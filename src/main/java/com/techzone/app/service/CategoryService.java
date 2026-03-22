package com.techzone.app.service;

import com.techzone.app.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> listAll();
    Category getById(Long id);
    Category save(Category category);
    void delete(Long id);
}
