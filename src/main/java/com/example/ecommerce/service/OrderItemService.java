package com.example.ecommerce.service;

import com.example.ecommerce.dto.*;
import com.example.ecommerce.response.EcommerceResponse;
import org.springframework.http.ResponseEntity;

public interface OrderItemService {

  ResponseEntity<EcommerceResponse<OrderItemResponseDto>> createOrderItem(OrderItemRequestDto orderItemDto);

  ResponseEntity<EcommerceResponse<OrderItemResponseDto>> getOrderItem(Long orderItemId);

  ResponseEntity<EcommerceResponse<ListOfOrderItemResponseDto>> getOrderItems();

  ResponseEntity<EcommerceResponse<ListOfOrderItemResponseDto>> deleteOrderItem(Long orderItemId);

  ResponseEntity<EcommerceResponse<OrderItemResponseDto>> updateOrderItem(Long orderItemId, OrderItemRequestDto orderItemDto);

}
