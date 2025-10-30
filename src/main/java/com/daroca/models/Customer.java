package com.daroca.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@ToString
@EqualsAndHashCode(of = "id") 
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100)
    private String email;

    @Column
    private Double latitude;

    @Column
    private Double longitude;
}