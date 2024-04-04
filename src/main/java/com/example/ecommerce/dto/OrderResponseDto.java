package com.example.ecommerce.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

  private Long orderId;

  private LocalDateTime orderDate;

  private String orderStatus;

  private Double totalPrice;
}
