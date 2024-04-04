package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.OrderItemRequestDto;
import com.example.ecommerce.dto.OrderItemResponseDto;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import javax.imageio.ImageIO;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        imports = {Math.class, ImageIO.class, LocalDateTime.class, ZoneOffset.class})
public interface OrderItemMapper {

  OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

  @Mapping(target = "id",
          expression = "java(null)")
  @Mapping(target = "order",
          expression = "java(order)")
  @Mapping(target = "product",
          expression = "java(product)")
  @Mapping(target = "price",
          expression = "java(product.getPrice())")
  OrderItem mapOrderItemRequestDtoToOrderItem(OrderItemRequestDto orderItemDto, Product product,
                                              Order order);

  @Mapping(target = "orderItemId", expression = "java(orderItem.getId())")
  OrderItemResponseDto mapOrderItemToOrderItemResponseDto(OrderItem orderItem);

  @Mapping(target = "id",
          expression = "java(savedOrderItem.getId())" )
  @Mapping(target = "order",
          expression = "java(orderItemDto.getOrderId() == null ? " +
                  "savedOrderItem.getOrder() : order)")
  @Mapping(target = "product",
          expression = "java(orderItemDto.getProductId() == null ? " +
                  "savedOrderItem.getProduct() : product)")
  @Mapping(target = "quantity",
          expression = "java(orderItemDto.getQuantity() == null ? " +
                  "savedOrderItem.getQuantity() : orderItemDto.getQuantity())")
  @Mapping(target = "price",
          expression = "java(savedOrderItem.getPrice())")
  OrderItem mapToUpdateOrderItem(OrderItemRequestDto orderItemDto, OrderItem savedOrderItem,
                                 Product product, Order order);

}
