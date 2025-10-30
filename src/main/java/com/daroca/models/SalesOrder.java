package com.daroca.models;

import jakarta.persistence.*;
import lombok.*; 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sales_order") 
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@ToString(exclude = "items") 
@EqualsAndHashCode(of = "salesOrderId") 
public class SalesOrder{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salesOrderId;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column
    private LocalDate estimatedDeliveryDate;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(nullable = false)
    private Double total;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SalesOrder_Customer"))
    private Customer customer;

    @OneToMany(
        mappedBy = "salesOrder",
        cascade = CascadeType.ALL, 
        orphanRemoval = true 
    )
    private List<SalesOrderItem> items;

}