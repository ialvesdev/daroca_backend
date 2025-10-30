package com.daroca.repositories;

import com.daroca.models.SalesOrderItem;
import com.daroca.models.SalesOrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, SalesOrderItemKey> { }