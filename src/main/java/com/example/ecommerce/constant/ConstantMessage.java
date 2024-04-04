package com.example.ecommerce.constant;

import org.springframework.stereotype.Component;

@Component
public class ConstantMessage {

  public static final String INVALID_REQUEST_STRUCTURE = "Request structure is not valid";
  public static final String EXPECTED_REQUEST_PARAMETERS_NOT_PROVIDED = "Current request does not contain expected parameter(s)";
  public static final String NUMBER_FORMAT_EXCEPTION_MESSAGE = "URL parameter(s) does not conform to the required numeric format";
  public static final String INTERNAL_ERROR = "Operation was not successful due to an internal error";
  public static final String PRODUCT_DOES_NOT_EXIST = "Product does not exist";
  public static final String ORDERED_STATUS = "ORDERED";
  public static final String ORDER_DOES_NOT_EXIST = "Order does not exist";
  public static final String ORDER_ITEM_DOES_NOT_EXIST = "Order item does not exist";
  public static final String INSUFFICIENT_QUANTITY = "Insufficient stock for the requested quantity";

}
