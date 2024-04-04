package com.example.ecommerce.service;

import com.example.ecommerce.dto.ListOfProductResponseDto;
import com.example.ecommerce.dto.ProductRequestDto;
import com.example.ecommerce.dto.ProductResponseDto;
import com.example.ecommerce.response.EcommerceResponse;
import org.springframework.http.ResponseEntity;

public interface ProductService {

  ResponseEntity<EcommerceResponse<ProductResponseDto>> createProduct(ProductRequestDto productDto);

  ResponseEntity<EcommerceResponse<ProductResponseDto>> getProduct(Long productId);

  ResponseEntity<EcommerceResponse<ListOfProductResponseDto>> getProducts();

  ResponseEntity<EcommerceResponse<ListOfProductResponseDto>> deleteProduct(Long productId);

  ResponseEntity<EcommerceResponse<ProductResponseDto>> updateProduct(Long productId, ProductRequestDto productDto);

}
