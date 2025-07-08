package com.dtech.Ecommerce.order.orderService.impl;

import com.dtech.Ecommerce.common.OrderStatusDTO;
import com.dtech.Ecommerce.common.SearchDTO;
import com.dtech.Ecommerce.fileService.dto.CommonResponse;
import com.dtech.Ecommerce.order.orderDTO.OrderDTO;
import com.dtech.Ecommerce.order.orderModel.Order;
import com.dtech.Ecommerce.product.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderServiceImpl {
    OrderDTO createOrder(OrderDTO orderDTO);

    CommonResponse getOrderCount();

    Page<OrderDTO> getAllOrders(Pageable pageable);

    Page<OrderDTO> searchOrder(SearchDTO search, Pageable pageable);

    OrderStatusDTO getPendingOrdersCount();

    Page<OrderDTO> getOrderByCustomerId(Integer customerId, Pageable pageable);

    List<OrderDTO> getRecentOrderById(Integer customerId);

    List<ProductDTO> getTopSaleProducts();

    void changeOrderStatusById(Integer orderId, String orderStatusDTO);

    List<OrderDTO> getOrderToReviewByCustomerId(Integer customerId);

    void reviewTrue(Integer orderId);
}
