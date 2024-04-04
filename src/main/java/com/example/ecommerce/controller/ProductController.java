package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ListOfProductResponseDto;
import com.example.ecommerce.dto.ProductRequestDto;
import com.example.ecommerce.dto.ProductResponseDto;
import com.example.ecommerce.dto.ValidationGroups;
import com.example.ecommerce.exception.GlobalExceptionHandler;
import com.example.ecommerce.response.EcommerceResponse;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Import(GlobalExceptionHandler.class)
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<EcommerceResponse<ProductResponseDto>> createProduct(
          @RequestBody @Validated(ValidationGroups.CreateProduct.class) ProductRequestDto productDto)
  {
    return productService.createProduct(productDto);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<EcommerceResponse<ProductResponseDto>> getProduct(
          @PathVariable Long productId)
  {
    return productService.getProduct(productId);
  }

  @GetMapping
  public ResponseEntity<EcommerceResponse<ListOfProductResponseDto>> getProducts()
  {
    return productService.getProducts();
  }

  @PutMapping("/{productId}")
  public ResponseEntity<EcommerceResponse<ProductResponseDto>> updateProduct(
          @PathVariable Long productId, @RequestBody @Validated(ValidationGroups.UpdateProduct.class) ProductRequestDto productDto)
  {
    return productService.updateProduct(productId, productDto);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<EcommerceResponse<ListOfProductResponseDto>> deleteProduct(
          @PathVariable Long productId)
  {
    return productService.deleteProduct(productId);
  }

}
