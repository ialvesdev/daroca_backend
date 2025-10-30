package com.daroca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*; 

@Embeddable
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@EqualsAndHashCode
public class SalesOrderItemKey {
    
    @Column(name = "sales_order_id")
    private Integer salesOrderId;

    @Column(name = "product_id")
    private Integer productId;

}