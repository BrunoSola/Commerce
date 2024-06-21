package com.brunosola.commerce.repositories;

import com.brunosola.commerce.entities.OrderItem;
import com.brunosola.commerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
