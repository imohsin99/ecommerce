package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

  @NotNull
  @Pattern(regexp = "(?i)(delivered|cancelled)", message = "must have an acceptable value")
  private String orderStatus;

}
