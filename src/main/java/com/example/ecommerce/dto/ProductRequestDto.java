package com.example.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

  @NotNull(groups = ValidationGroups.CreateProduct.class)
  private String name;

  @NotNull(groups = ValidationGroups.CreateProduct.class)
  private String description;

  @NotNull(groups = ValidationGroups.CreateProduct.class)
  @Min(value = 0, message = "enter a positive price")
  private Double price;

  @NotNull(groups = ValidationGroups.CreateProduct.class)
  @Length(min = 0, max = 32, message = "enter a correct sku number")
  private String sku;

  private String imageLink;

  @NotNull(groups = ValidationGroups.CreateProduct.class)
  @Min(value = 0, message = "enter a positive stock")
  private Integer stockQuantity;

}
