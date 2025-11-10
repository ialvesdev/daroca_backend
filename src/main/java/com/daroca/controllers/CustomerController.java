package com.daroca.controllers;

import com.daroca.models.Customer;
import com.daroca.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers") 
public class CustomerController {

    private final CustomerRepository repository;

    @Autowired
    public CustomerController(CustomerRepository repository){
        this.repository = repository;
    }
      
    @GetMapping
    public ResponseEntity<List<Customer>> all(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}") 
    public ResponseEntity<Customer> one(@PathVariable Integer id){
        return repository.findById(id)
                .map(customer -> ResponseEntity.ok(customer)) 
                .orElse(ResponseEntity.notFound().build()); 
    }

    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping 
    public ResponseEntity<Customer> save(@RequestBody Customer newCustomer){
        Customer savedCustomer = repository.save(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @PutMapping("/{id}") 
    public ResponseEntity<Customer> replace(@RequestBody Customer newCustomer, @PathVariable Integer id){
        return repository.findById(id)
                .map(customer -> { 
                    customer.setName(newCustomer.getName());
                    customer.setEmail(newCustomer.getEmail());
                    customer.setLatitude(newCustomer.getLatitude());
                    customer.setLongitude(newCustomer.getLongitude());
                    return ResponseEntity.ok(repository.save(customer)); 
                })
                .orElse(ResponseEntity.notFound().build()); 
    }
}