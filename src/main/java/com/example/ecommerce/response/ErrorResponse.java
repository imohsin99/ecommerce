package com.example.ecommerce.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {

  String errorOnField;
  String errorMessage;
  public ErrorResponse(String responseMessage){
    this.errorMessage = responseMessage;
  }

  public ErrorResponse(String errorOnField,String responseMessage){
    this.errorOnField = errorOnField;
    this.errorMessage = responseMessage;
  }

}
