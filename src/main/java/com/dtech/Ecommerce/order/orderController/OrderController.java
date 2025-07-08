package com.dtech.Ecommerce.order.orderController;

import com.dtech.Ecommerce.common.OrderStatusDTO;
import com.dtech.Ecommerce.common.SearchDTO;
import com.dtech.Ecommerce.order.orderDTO.OrderDTO;
import com.dtech.Ecommerce.order.orderService.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;


    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.createOrder(orderDTO), HttpStatus.OK);
    }

    @GetMapping("/orderCount")
    public ResponseEntity<?> getOrderCount() {
        return new ResponseEntity<>(orderService.getOrderCount(), HttpStatus.OK);
    }

    @GetMapping("/allOrders")
    public ResponseEntity<?> getAllOrders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "12") int size) {
            Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(orderService.getAllOrders(pageable), HttpStatus.OK);
    }

    @PostMapping("/searchOrder")
    public ResponseEntity<?> searchOrder(@RequestBody SearchDTO search , @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(orderService.searchOrder(search,pageable), HttpStatus.OK);
    }

    @GetMapping("/pendingOrdersCount")
    public ResponseEntity<?> getPendingOrdersCount() {
        return new ResponseEntity<>(orderService.getPendingOrdersCount(), HttpStatus.OK);
    }

    @GetMapping("getOrderByCustomerId/{customerId}")
    public ResponseEntity<?> getOrderByCustomerId(@PathVariable Integer customerId, @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(orderService.getOrderByCustomerId(customerId,pageable), HttpStatus.OK);
    }

    @PostMapping("/getRecentOrderById/{customerId}")
    public ResponseEntity<?> getRecentOrderById(@PathVariable Integer customerId) {
        return new ResponseEntity<>(orderService.getRecentOrderById(customerId), HttpStatus.OK);
    }

    @GetMapping("/topSaleProducts")
    public ResponseEntity<?> getTopSaleProducts() {
        return new ResponseEntity<>(orderService.getTopSaleProducts(), HttpStatus.OK);
    }

    @PostMapping("/changeOrderStatus/{id}/{orderStatus}")
    public ResponseEntity<?> changeOrderStatusById(@PathVariable Integer id, @PathVariable String orderStatus) {
        orderService.changeOrderStatusById(id,orderStatus);
        return ResponseEntity.ok("Status Change Success");
    }

    @PostMapping("/getOrderToReviewByCustomerId/{customerId}")
    public ResponseEntity<?> getOrderToReviewByCustomerId(@PathVariable Integer customerId) {
        return new ResponseEntity<>(orderService.getOrderToReviewByCustomerId(customerId), HttpStatus.OK);
    }
}
