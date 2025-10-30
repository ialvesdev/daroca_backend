package com.daroca.controllers;

import com.daroca.models.Product;
import com.daroca.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products") 
public class ProductController {

    private final ProductRepository repository;

    @Autowired
    public ProductController(ProductRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Product>> all(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{productId}") 
    public ResponseEntity<Product> one(@PathVariable Integer productId){
        return repository.findById(productId)
                .map(product -> ResponseEntity.ok(product)) 
                .orElse(ResponseEntity.notFound().build()); 
    }

    @DeleteMapping("/{productId}") 
    public ResponseEntity<Void> delete(@PathVariable Integer productId){
        repository.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping 
    public ResponseEntity<Product> save(@RequestBody Product newProduct){
        Product savedProduct = repository.save(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{productId}") 
    public ResponseEntity<Product> replace(@RequestBody Product newProduct, @PathVariable Integer productId){
        return repository.findById(productId)
                .map(product -> { 
                    product.setName(newProduct.getName());
                    product.setUnitPrice(newProduct.getUnitPrice());
                    product.setProductCategory(newProduct.getProductCategory());
                    
                    return ResponseEntity.ok(repository.save(product));
                })
                .orElse(ResponseEntity.notFound().build()); 
    }
}