package com.dtech.Ecommerce.order.orderRepo;


import com.dtech.Ecommerce.order.orderModel.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import java.util.Date;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    Page<Order> findByIdAndOrderStatusAndOrderDateBetween(Integer id, String orderStatus, Date orderDate, Date orderDate2, Pageable pageable);

    Page<Order> findByOrderStatusAndOrderDateBetween(String orderStatus, Date orderDate, Date orderDate2, Pageable pageable);

    Page<Order> findByOrderDateBetween(Date orderDate, Date orderDate2, Pageable pageable);

    Page<Order> findByIdAndOrderDateBetween(Integer id,Date fromDate,Date toDate, Pageable pageable);

    List<Order> findByOrderStatus(String orderStatus);

    Page<Order> findByCustomer_Id(Integer customerId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId ORDER BY o.orderDate DESC")
    List<Order> findTop5ByCustomerIdOrderByOrderDateDesc(@Param("customerId") Integer customerId);

    List<Order> findByCustomer_IdAndOrderStatusAndReview(Integer customerId, String orderStatus, boolean review);
}
