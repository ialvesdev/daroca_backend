package com.daroca.models;

import jakarta.persistence.*;
import lombok.*; 

@Entity
@Table
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@ToString 
@EqualsAndHashCode(of = "productId") 
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column
    private Double unitPrice;

    @ManyToOne 
    @JoinColumn(name = "product_category_id", foreignKey = @ForeignKey(name = "FK_Product_ProductCategory"))
    private ProductCategory productCategory;

}