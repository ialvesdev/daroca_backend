package com.daroca.controllers;

import com.daroca.models.Customer;
import com.daroca.models.Product;
import com.daroca.models.SalesOrder;
import com.daroca.models.SalesOrderItem;
import com.daroca.models.SalesOrderItemKey; 
import com.daroca.repositories.SalesOrderRepository;
import com.daroca.repositories.CustomerRepository;
import com.daroca.repositories.ProductRepository;
import com.daroca.dto.SalesOrderRequestDTO;
import com.daroca.dto.OrderItemRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional; 
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/sales-orders")
public class SalesOrderController {

    private final SalesOrderRepository salesOrderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SalesOrderController(
        SalesOrderRepository salesOrderRepository,
        CustomerRepository customerRepository,
        ProductRepository productRepository
    ){
        this.salesOrderRepository = salesOrderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<SalesOrder>> getAllOrders() {
        return ResponseEntity.ok(salesOrderRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrder> getOrderById(@PathVariable Integer id) {
        return salesOrderRepository.findById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build()); 
    }

    @PostMapping
    @Transactional
    public ResponseEntity<SalesOrder> createOrder(@RequestBody SalesOrderRequestDTO orderRequest) {
        Optional<Customer> customerOpt = customerRepository.findById(orderRequest.getCustomerId());
        if (customerOpt.isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }
        Customer customer = customerOpt.get();
        SalesOrder newOrder = new SalesOrder();
        newOrder.setCustomer(customer);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatus("PENDING"); 

        List<SalesOrderItem> orderItems = new ArrayList<>();
        double totalOrder = 0.0;
        for (OrderItemRequestDTO itemDTO : orderRequest.getItems()) {
            Optional<Product> productOpt = productRepository.findById(itemDTO.getProductId());
            if (productOpt.isEmpty()) {
                return ResponseEntity.badRequest().build(); 
            }
            Product product = productOpt.get();
            SalesOrderItem orderItem = new SalesOrderItem();
            orderItem.setSalesOrder(newOrder); 
            orderItem.setProduct(product);     
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setSalesOrderItemId(new SalesOrderItemKey());
            orderItems.add(orderItem);
            totalOrder += (product.getUnitPrice() * itemDTO.getQuantity());
        }

        newOrder.setTotal(totalOrder);
        newOrder.setItems(orderItems); 
        SalesOrder savedOrder = salesOrderRepository.save(newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        if (!salesOrderRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); 
        }
        salesOrderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}