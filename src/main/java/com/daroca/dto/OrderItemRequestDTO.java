package com.daroca.dto;

import lombok.Getter;

@Getter
public class OrderItemRequestDTO {
    private Integer productId;
    private Integer quantity;
}