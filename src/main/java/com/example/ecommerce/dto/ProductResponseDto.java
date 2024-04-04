package com.example.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

  private Long productId;

  private String name;

  private String description;

  private double price;

  private String sku;

  private String imageLink;

  private Integer stockQuantity;

}
