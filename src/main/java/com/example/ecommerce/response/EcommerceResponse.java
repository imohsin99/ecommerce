package com.example.ecommerce.response;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class EcommerceResponse<T> {

  private List<ErrorResponse> errors;
  private T payload;

  public EcommerceResponse(){
    this.errors = new ArrayList<>();
  }

  public EcommerceResponse(T payload){
    this.errors = new ArrayList<>();
    this.payload = payload;
  }

  public void setPayload(T payload){
    this.payload = payload;
  }

  public void addError(String errorMessage){
    this.errors.add(new ErrorResponse(errorMessage));
  }

}
