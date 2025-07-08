package com.dtech.Ecommerce.order.orderRepo;

import com.dtech.Ecommerce.order.orderModel.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {
    @Query("SELECT oi.productId FROM OrderItem oi GROUP BY oi.productId ORDER BY COUNT(oi.productId) DESC")
    List<Integer> findTop3ProductIds();
}
