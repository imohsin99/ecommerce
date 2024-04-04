package com.example.ecommerce.mapper;

import com.example.ecommerce.constant.ConstantMessage;
import com.example.ecommerce.dto.OrderRequestDto;
import com.example.ecommerce.dto.OrderResponseDto;
import com.example.ecommerce.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import javax.imageio.ImageIO;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        imports = {Math.class, ImageIO.class, LocalDateTime.class, ZoneOffset.class, ConstantMessage.class})
public interface OrderMapper {

  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  @Mapping(target = "orderDate", expression = "java(LocalDateTime.now(ZoneOffset.UTC))")
  @Mapping(target = "orderStatus", expression = "java(ConstantMessage.ORDERED_STATUS)")
  @Mapping(target = "totalPrice", expression = "java(0.0)")
  Order mapToOrder(Order order);

  @Mapping(target = "orderId", expression = "java(order.getId())")
  OrderResponseDto mapOrderToOrderResponseDto(Order order);

  @Mapping(target = "id",
          expression = "java(savedOrder.getId())" )
  @Mapping(target = "orderDate",
          expression = "java(savedOrder.getOrderDate())")
  @Mapping(target = "totalPrice",
          expression = "java(savedOrder.getTotalPrice())")
  @Mapping(target = "orderStatus",
          expression = "java(orderDto.getOrderStatus() == null ? " +
                  "savedOrder.getOrderStatus() : orderDto.getOrderStatus().toUpperCase())")
  Order mapToUpdateOrder(OrderRequestDto orderDto, Order savedOrder);

}
