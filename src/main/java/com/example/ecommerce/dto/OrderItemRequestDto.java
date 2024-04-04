package com.example.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDto {

  @NotNull(groups = ValidationGroups.CreateOrderItem.class)
  private Long orderId;

  @NotNull(groups = ValidationGroups.CreateOrderItem.class)
  private Long productId;

  @NotNull(groups = ValidationGroups.CreateOrderItem.class)
  @Min(value = 1, message = "quantity must be greater than 0")
  private Integer quantity;

}
