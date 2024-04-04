package com.example.ecommerce.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListOfOrderResponseDto {

  List<OrderResponseDto> orders;

}
