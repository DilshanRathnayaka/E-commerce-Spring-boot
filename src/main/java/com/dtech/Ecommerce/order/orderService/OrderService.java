package com.dtech.Ecommerce.order.orderService;

import com.dtech.Ecommerce.auth.authModel.User;
import com.dtech.Ecommerce.auth.authRepo.UserRepo;
import com.dtech.Ecommerce.auth.authService.EmailService;
import com.dtech.Ecommerce.auth.dto.MailBody;
import com.dtech.Ecommerce.common.OrderStatusDTO;
import com.dtech.Ecommerce.common.SearchDTO;
import com.dtech.Ecommerce.customer.customerDTO.AddressDTO;
import com.dtech.Ecommerce.customer.customerDTO.CustomerDTO;
import com.dtech.Ecommerce.customer.mapper.CountryRepo;
import com.dtech.Ecommerce.customer.mapper.CustomerMapper;
import com.dtech.Ecommerce.customer.model.Address;
import com.dtech.Ecommerce.customer.model.Country;
import com.dtech.Ecommerce.customer.model.Customer;
import com.dtech.Ecommerce.customer.repository.CustomerRepo;
import com.dtech.Ecommerce.exeption.CustomExeption;
import com.dtech.Ecommerce.exeption.OrderExeption;
import com.dtech.Ecommerce.fileService.dto.CommonResponse;
import com.dtech.Ecommerce.order.orderDTO.OrderDTO;
import com.dtech.Ecommerce.order.orderDTO.OrderItemDTO;
import com.dtech.Ecommerce.order.orderMapper.OrderMapper;
import com.dtech.Ecommerce.order.orderModel.Order;
import com.dtech.Ecommerce.order.orderModel.OrderItem;
import com.dtech.Ecommerce.order.orderRepo.OrderItemRepo;
import com.dtech.Ecommerce.order.orderRepo.OrderRepo;
import com.dtech.Ecommerce.order.orderService.impl.OrderServiceImpl;
import com.dtech.Ecommerce.product.dto.ProductDTO;
import com.dtech.Ecommerce.product.mapper.ProductMapper;
import com.dtech.Ecommerce.product.model.Product;
import com.dtech.Ecommerce.product.model.Stock;
import com.dtech.Ecommerce.product.repository.ProductRepo;
import com.dtech.Ecommerce.product.repository.StockRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements OrderServiceImpl {

    private final CustomerRepo customerRepo;
    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;
    private final StockRepo stockRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final EmailService emailService;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        try {
            Order order = orderMapper.toOrder(orderDTO);
            if (orderDTO.getCustomer().getId() != null) {
                Optional<Customer> customer = customerRepo.findById(orderDTO.getCustomer().getId());
                if (customer.isEmpty()) {
                    throw new CustomExeption("Customer not found");
                }
                if (customer.get().getAddress().isEmpty()) {
                    throw new CustomExeption("Customer address not found");
                }
            } else {
                throw new CustomExeption("Login Required");
            }
            for (OrderItem orderItemDTO : order.getOrderItem()) {
                orderItemDTO.setOrder(order);
                try {
                    Stock stock = new Stock();
                    if (orderItemDTO.getVariantId() != null) {
                        stock = stockRepo.findStockByVariant_Id(orderItemDTO.getVariantId());
                    }
                    if (orderItemDTO.getProductId() != null) {
                        stock = stockRepo.findStockByProduct_Id(orderItemDTO.getProductId());
                    }

                    int newQuantity = stock.getQuantity() - orderItemDTO.getQuantity();
                    System.out.println("newQuantity: " + newQuantity);
                    System.out.println("stock.getQuantity(): " + stock.getQuantity());
                    System.out.println("orderItemDTO.getQuantity(): " + orderItemDTO.getQuantity());
                    if (newQuantity < 0) {
                        throw new CustomExeption("Only have " + stock.getQuantity() + " items in stock");
                    }
                    stock.setQuantity(newQuantity);
                    stockRepo.save(stock);
                } catch (CustomExeption e) {
                    throw new CustomExeption(e.getMessage());
                }
            }
            OrderDTO orderDTO1 = orderMapper.toOrderDTO(orderRepo.save(order));
            orderDTO1.getCustomer().setUser(null);
//            MailBody mailBody = MailBody.builder()
//                    .to(orderDTO1.getCustomer().getEmail())
//                    .subject("Order Status Update")
//                    .text("Dear Customer,\n\nWe are pleased to inform you that the status of your order has been updated. Please find the details below:\n\nOrder ID: " + orderDTO1.getId() + "\nNew Status: " + orderDTO1.getOrderStatus() + "\n\nThank you for shopping with us.\n\nBest regards,\nEcommerce Team")
//                    .build();
//
//            emailService.sendSimpleMessage(mailBody);
            return orderDTO1;


        } catch (CustomExeption e) {
            throw new CustomExeption(e.getMessage());
        }
    }

    @Override
    public CommonResponse getOrderCount() {
        CommonResponse commonResponse = new CommonResponse();
        long count = orderRepo.count();
        commonResponse.setMessage(Long.toString(count));
        return commonResponse;
    }

    @Override
    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        return orderRepo.findAll(pageable).map(orderMapper::toOrderDTO);
    }

    public Page<OrderDTO> searchOrder(SearchDTO search, Pageable pageable) {
        try {
            if (search.getOrderId() == null && search.getFromDate() != null && search.getToDate() != null && search.getOrderStatus() != null) {
                return orderRepo.findByOrderStatusAndOrderDateBetween(search.getOrderStatus(), search.getFromDate(), search.getToDate(), pageable).map(orderMapper::toOrderDTO);
            }
            if (search.getOrderId() == null && search.getFromDate() != null && search.getToDate() != null) {
                return orderRepo.findByOrderDateBetween(search.getFromDate(), search.getToDate(), pageable).map(orderMapper::toOrderDTO);
            }
            if (search.getOrderId() != null && search.getFromDate() != null && search.getToDate() != null && search.getOrderStatus() == null) {
                return orderRepo.findByIdAndOrderDateBetween(search.getOrderId(), search.getFromDate(), search.getToDate(), pageable).map(orderMapper::toOrderDTO);
            }
            return orderRepo.findByIdAndOrderStatusAndOrderDateBetween(search.getOrderId(), search.getOrderStatus(), search.getFromDate(), search.getToDate(), pageable).map(orderMapper::toOrderDTO);
        } catch (Exception e) {
            throw new CustomExeption(e.getMessage());
        }

    }

    @Override
    public OrderStatusDTO getPendingOrdersCount() {
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        try {
            List<Order> orders = orderRepo.findAll();
            orders.stream().filter(order -> order.getOrderStatus().equals("Pending")).forEach(order -> orderStatusDTO.setPending(Integer.toString(orders.size())));
            orders.stream().filter(order -> order.getOrderStatus().equals("Shipped")).forEach(order -> orderStatusDTO.setPending(Integer.toString(orders.size())));
            orders.stream().filter((order -> order.getOrderStatus().equals("Delivered"))).forEach(order -> orderStatusDTO.setDelivered(Integer.toString(orders.size())));
            orders.stream().filter((order -> order.getOrderStatus().equals("Cancelled"))).forEach(order -> orderStatusDTO.setCancelled(Integer.toString(orders.size())));
            return orderStatusDTO;
        } catch (Exception e) {
            throw new CustomExeption(e.getMessage());
        }

    }

    @Override
    public Page<OrderDTO> getOrderByCustomerId(Integer customerId, Pageable page) {
        Page<Order> orders = orderRepo.findByCustomer_Id(customerId, page);
        return orders.map(orderMapper::toOrderDTO);
    }

    @Override
    public List<OrderDTO> getRecentOrderById(Integer customerId) {
        List<Order> orders = orderRepo.findTop5ByCustomerIdOrderByOrderDateDesc(customerId);
        return orders.stream().limit(5).map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getTopSaleProducts() {
        List<Integer> topProductIds = orderItemRepo.findTop3ProductIds();
        List<Product> topProducts = productRepo.findAllById(topProductIds);
        return topProducts.stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void changeOrderStatusById(Integer orderId, String orderStatusDTO) {
        try {
            Optional<Order> order = orderRepo.findById(orderId);
            if (order.isPresent()) {
                order.get().setOrderStatus(orderStatusDTO);
                orderRepo.save(order.get());

                MailBody mailBody = MailBody.builder()
                        .to(order.get().getCustomer().getEmail())
                        .subject("Order Status has Been Changed")
                        .text("Your Order Has been : " + order.get().getOrderStatus())
                        .build();

                emailService.sendSimpleMessage(mailBody);
            }
        } catch (OrderExeption e) {
            throw new OrderExeption(e.getMessage());
        }
    }

    @Override
    public List<OrderDTO> getOrderToReviewByCustomerId(Integer customerId) {
        try {
            List<Order> orders = orderRepo.findByCustomer_IdAndOrderStatusAndReview(customerId, "delivered", false);
            return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
        } catch (OrderExeption e) {
            throw new OrderExeption(e.getMessage());
        }
    }

    public void reviewTrue(Integer orderId) {
        orderRepo.findById(orderId).ifPresent(order -> {
            order.setReview(true);
        });
    }


}
