package com.example.ecommerce.dto;

import jakarta.validation.groups.Default;

/**
 * Utility class to distinct CRUD validations.<br>
 * <br>
 * Used with the
 * {@link org.springframework.validation.annotation.Validated @Validated}
 * Spring annotation.
 */
public final class ValidationGroups {

  private ValidationGroups() {}

  public interface CreateOrderItem extends Default {}
  public interface UpdateOrderItem extends Default {}
  public interface CreateProduct extends Default {}
  public interface UpdateProduct extends Default {}

}
