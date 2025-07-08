package com.dtech.Ecommerce.order.orderMapper;

import com.dtech.Ecommerce.order.orderDTO.OrderDTO;
import com.dtech.Ecommerce.order.orderModel.Order;
import jdk.jfr.Name;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Named("toOrderDTO")
    OrderDTO toOrderDTO(Order order);

    @Named("toOrder")
    Order toOrder(OrderDTO orderDTO);

    @IterableMapping(qualifiedByName = "toOrder")
    List<Order> toOrders(List<OrderDTO> orderDTO);

    @IterableMapping(qualifiedByName = "toOrderDTO")
    List<OrderDTO> toOrderDTOs(List<Order> order);
}
