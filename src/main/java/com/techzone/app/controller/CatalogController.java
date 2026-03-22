package com.techzone.app.controller;

import com.techzone.app.entity.Brand;
import com.techzone.app.entity.Category;
import com.techzone.app.entity.Product;
import com.techzone.app.entity.Supplier;
import com.techzone.app.service.BrandService;
import com.techzone.app.service.CategoryService;
import com.techzone.app.service.ProductService;
import com.techzone.app.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class CatalogController {

    private final CategoryService categoryService;
    private final BrandService brandService;
    private final SupplierService supplierService;
    private final ProductService productService;

    @GetMapping("/customer/products-old")
    public String customerProducts(Model model) {
        model.addAttribute("products", productService.listAll());
        model.addAttribute("categories", categoryService.listAll());
        model.addAttribute("brands", brandService.listAll());
        model.addAttribute("suppliers", supplierService.listAll());
        return "customer/products";
    }

    @GetMapping("/staff/manage/products")
    public String staffProducts(Model model) {
        model.addAttribute("products", productService.listAll());
        model.addAttribute("categories", categoryService.listAll());
        model.addAttribute("brands", brandService.listAll());
        model.addAttribute("suppliers", supplierService.listAll());
        return "staff/manage/products";
    }

    @PostMapping("/staff/manage/products")
    public String saveProduct(@RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam double price,
                              @RequestParam int stock,
                              @RequestParam Long categoryId,
                              @RequestParam Long brandId,
                              @RequestParam Long supplierId) {

        Product product = new Product();
        if (id != null) {
            product = productService.getById(id);
        }
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(categoryService.getById(categoryId));
        product.setBrand(brandService.getById(brandId));
        product.setSupplier(supplierService.getById(supplierId));

        productService.save(product);
        return "redirect:/staff/manage/products";
    }

    @GetMapping("/staff/manage/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/staff/manage/products";
    }

    @GetMapping("/staff/manage/category")
    public String manageCategories(Model model) {
        model.addAttribute("categories", categoryService.listAll());
        return "staff/manage/categories";
    }

    @PostMapping("/staff/manage/category")
    public String saveCategory(@RequestParam(required = false) Long id,
                               @RequestParam String name,
                               @RequestParam(required = false) String description) {
        Category category = id != null ? categoryService.getById(id) : new Category();
        category.setName(name);
        category.setDescription(description);
        categoryService.save(category);
        return "redirect:/staff/manage/category";
    }

    @GetMapping("/staff/manage/category/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/staff/manage/category";
    }

    @GetMapping("/staff/manage/brand")
    public String manageBrands(Model model) {
        model.addAttribute("brands", brandService.listAll());
        return "staff/manage/brands";
    }

    @PostMapping("/staff/manage/brand")
    public String saveBrand(@RequestParam(required = false) Long id,
                            @RequestParam String name,
                            @RequestParam(required = false) String description) {
        Brand brand = id != null ? brandService.getById(id) : new Brand();
        brand.setName(name);
        brand.setDescription(description);
        brandService.save(brand);
        return "redirect:/staff/manage/brand";
    }

    @GetMapping("/staff/manage/brand/delete/{id}")
    public String deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
        return "redirect:/staff/manage/brand";
    }

    @GetMapping("/staff/manage/supplier")
    public String manageSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.listAll());
        return "staff/manage/suppliers";
    }

    @PostMapping("/staff/manage/supplier")
    public String saveSupplier(@RequestParam(required = false) Long id,
                               @RequestParam String name,
                               @RequestParam(required = false) String contactInfo) {
        Supplier supplier = id != null ? supplierService.getById(id) : new Supplier();
        supplier.setName(name);
        supplier.setContactInfo(contactInfo);
        supplierService.save(supplier);
        return "redirect:/staff/manage/supplier";
    }

    @GetMapping("/staff/manage/supplier/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierService.delete(id);
        return "redirect:/staff/manage/supplier";
    }
}
