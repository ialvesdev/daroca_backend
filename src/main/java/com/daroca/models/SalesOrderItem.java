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
@EqualsAndHashCode(of = "salesOrderItemId") 
public class SalesOrderItem {

    @EmbeddedId
    private SalesOrderItemKey salesOrderItemId;

    @ManyToOne
    @MapsId("salesOrderId") 
    @JoinColumn(name = "sales_order_id", foreignKey = @ForeignKey(name = "FK_SalesOrderItem_Sales"))
    private SalesOrder salesOrder;

    @ManyToOne 
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_SalesOrderItem_Product"))
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

}