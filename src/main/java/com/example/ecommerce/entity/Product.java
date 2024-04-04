package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private Double price;

  private String sku;

  private String imageLink;

  private Integer stockQuantity;

  @OneToMany(mappedBy = "product")
  private Set<OrderItem> orderItems;

}
