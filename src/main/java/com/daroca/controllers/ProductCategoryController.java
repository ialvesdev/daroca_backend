package com.daroca.controllers;

import com.daroca.models.ProductCategory;
import com.daroca.repositories.ProductCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product-categories")
public class ProductCategoryController {

    private final ProductCategoryRepository repository;

    @Autowired
    public ProductCategoryController(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> all() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{productCategoryId}") 
    public ResponseEntity<ProductCategory> one(@PathVariable Integer productCategoryId) {
        return repository.findById(productCategoryId)
                .map(category -> ResponseEntity.ok(category)) 
                .orElse(ResponseEntity.notFound().build()); 
    }

    @DeleteMapping("/{productCategoryId}") 
    public ResponseEntity<Void> delete(@PathVariable Integer productCategoryId) {
        repository.deleteById(productCategoryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping 
    public ResponseEntity<ProductCategory> save(@RequestBody ProductCategory newProductCategory) {
        ProductCategory savedCategory = repository.save(newProductCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{productCategoryId}") 
    public ResponseEntity<ProductCategory> replace(@RequestBody ProductCategory newProductCategory, @PathVariable Integer productCategoryId) {
        return repository.findById(productCategoryId)
                .map(category -> { 
                    category.setName(newProductCategory.getName());
                    return ResponseEntity.ok(repository.save(category));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}