package com.daroca.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class SalesOrderRequestDTO {
    private Integer customerId;
    private List<OrderItemRequestDTO> items;
}