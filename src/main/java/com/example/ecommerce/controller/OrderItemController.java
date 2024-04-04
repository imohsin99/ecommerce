package com.example.ecommerce.controller;

import com.example.ecommerce.dto.*;
import com.example.ecommerce.exception.GlobalExceptionHandler;
import com.example.ecommerce.response.EcommerceResponse;
import com.example.ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Import(GlobalExceptionHandler.class)
@RequestMapping("/api/order-items")
public class OrderItemController {

  private final OrderItemService orderItemService;

  @PostMapping
  public ResponseEntity<EcommerceResponse<OrderItemResponseDto>> createOrderItem(
          @RequestBody @Validated(ValidationGroups.CreateOrderItem.class) OrderItemRequestDto orderItemDto)
  {
    return orderItemService.createOrderItem(orderItemDto);
  }

  @GetMapping("/{orderItemId}")
  public ResponseEntity<EcommerceResponse<OrderItemResponseDto>> getOrderItem(
          @PathVariable Long orderItemId)
  {
    return orderItemService.getOrderItem(orderItemId);
  }

  @GetMapping
  public ResponseEntity<EcommerceResponse<ListOfOrderItemResponseDto>> getOrderItems()
  {
    return orderItemService.getOrderItems();
  }

  @PutMapping("/{orderItemId}")
  public ResponseEntity<EcommerceResponse<OrderItemResponseDto>> updateOrderItem(
          @PathVariable Long orderItemId, @RequestBody @Validated(ValidationGroups.UpdateOrderItem.class) OrderItemRequestDto orderItemDto)
  {
    return orderItemService.updateOrderItem(orderItemId, orderItemDto);
  }

  @DeleteMapping("/{orderItemId}")
  public ResponseEntity<EcommerceResponse<ListOfOrderItemResponseDto>> deleteOrderItem(
          @PathVariable Long orderItemId)
  {
    return orderItemService.deleteOrderItem(orderItemId);
  }

}
