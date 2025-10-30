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
@EqualsAndHashCode(of = "productCategoryId")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productCategoryId;

    @Column(length = 50, nullable = false)
    private String name;
}