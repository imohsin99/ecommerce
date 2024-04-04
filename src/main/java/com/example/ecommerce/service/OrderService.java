package com.example.ecommerce.service;

import com.example.ecommerce.dto.ListOfOrderResponseDto;
import com.example.ecommerce.dto.OrderRequestDto;
import com.example.ecommerce.dto.OrderResponseDto;
import com.example.ecommerce.response.EcommerceResponse;
import org.springframework.http.ResponseEntity;

public interface OrderService {

  ResponseEntity<EcommerceResponse<OrderResponseDto>> createOrder();

  ResponseEntity<EcommerceResponse<OrderResponseDto>> getOrder(Long orderId);

  ResponseEntity<EcommerceResponse<ListOfOrderResponseDto>> getOrders();

  ResponseEntity<EcommerceResponse<ListOfOrderResponseDto>> deleteOrder(Long orderId);

  ResponseEntity<EcommerceResponse<OrderResponseDto>> updateOrder(Long orderId, OrderRequestDto orderDto);

}
