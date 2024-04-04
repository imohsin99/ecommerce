package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ListOfOrderResponseDto;
import com.example.ecommerce.dto.OrderRequestDto;
import com.example.ecommerce.dto.OrderResponseDto;
import com.example.ecommerce.exception.GlobalExceptionHandler;
import com.example.ecommerce.response.EcommerceResponse;
import com.example.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Import(GlobalExceptionHandler.class)
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<EcommerceResponse<OrderResponseDto>> createOrder()
  {
    return orderService.createOrder();
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<EcommerceResponse<OrderResponseDto>> getOrder(
          @PathVariable Long orderId)
  {
    return orderService.getOrder(orderId);
  }

  @GetMapping
  public ResponseEntity<EcommerceResponse<ListOfOrderResponseDto>> getOrders()
  {
    return orderService.getOrders();
  }

  @PutMapping("/{orderId}")
  public ResponseEntity<EcommerceResponse<OrderResponseDto>> updateOrder(
          @PathVariable Long orderId, @Validated @RequestBody OrderRequestDto orderDto)
  {
    return orderService.updateOrder(orderId, orderDto);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<EcommerceResponse<ListOfOrderResponseDto>> deleteOrder(
          @PathVariable Long orderId)
  {
    return orderService.deleteOrder(orderId);
  }

}
