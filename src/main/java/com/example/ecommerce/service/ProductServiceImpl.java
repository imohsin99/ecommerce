package com.example.ecommerce.service;

import com.example.ecommerce.constant.ConstantMessage;
import com.example.ecommerce.dto.ListOfProductResponseDto;
import com.example.ecommerce.dto.ProductRequestDto;
import com.example.ecommerce.dto.ProductResponseDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.response.EcommerceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private EcommerceResponse<ProductResponseDto> response;
  private EcommerceResponse<ListOfProductResponseDto> listOfProductResponse;

  @Override
  @Transactional
  public ResponseEntity<EcommerceResponse<ProductResponseDto>> createProduct(ProductRequestDto productDto) {
    response = new EcommerceResponse<>();
    Product product = productMapper.mapProductRequestDtoToProduct(productDto);
    Product savedProduct = productRepository.save(product);
    response.setPayload(productMapper.mapProductToProductResponseDto(savedProduct));
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<EcommerceResponse<ProductResponseDto>> getProduct(Long productId) {
    response = new EcommerceResponse<>();
    Product product = productRepository.findById(productId).orElse(null);
    ResponseEntity<EcommerceResponse<ProductResponseDto>> productResponse = checkProductExistence(product, response);
    if(productResponse != null) {
      return productResponse;
    }
    response.setPayload(productMapper.mapProductToProductResponseDto(product));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<EcommerceResponse<ListOfProductResponseDto>> getProducts() {
    listOfProductResponse = new EcommerceResponse<>();
    listOfProductResponse.setPayload(getAllProductsMappedWithProductResponseDto());
    return new ResponseEntity<>(listOfProductResponse, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity<EcommerceResponse<ListOfProductResponseDto>> deleteProduct(Long productId) {
    listOfProductResponse = new EcommerceResponse<>();
    Product product = productRepository.findById(productId).orElse(null);
    ResponseEntity<EcommerceResponse<ListOfProductResponseDto>> productResponse = checkProductExistence(product, listOfProductResponse);
    if(productResponse != null) {
      return productResponse;
    }
    productRepository.deleteById(productId);
    listOfProductResponse.setPayload(getAllProductsMappedWithProductResponseDto());
    return new ResponseEntity<>(listOfProductResponse, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity<EcommerceResponse<ProductResponseDto>> updateProduct(Long productId, ProductRequestDto productDto) {
    response = new EcommerceResponse<>();
    Product product = productRepository.findById(productId).orElse(null);
    ResponseEntity<EcommerceResponse<ProductResponseDto>> productResponse = checkProductExistence(product, response);
    if(productResponse != null) {
      return productResponse;
    }
    Product updateProduct = productRepository.save(productMapper.mapToUpdateProduct(productDto, product));
    response.setPayload(productMapper.mapProductToProductResponseDto(updateProduct));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private ListOfProductResponseDto getAllProductsMappedWithProductResponseDto() {
    ListOfProductResponseDto productResponse = new ListOfProductResponseDto();
    List<Product> products = productRepository.findAll();
    List<ProductResponseDto> getAllProducts = products
            .stream()
            .map(productMapper::mapProductToProductResponseDto)
            .sorted(Comparator.comparing(ProductResponseDto::getProductId))
            .toList();
    productResponse.setProducts(getAllProducts);
    return productResponse;
  }

  private <T> ResponseEntity<EcommerceResponse<T>> checkProductExistence(Product product, EcommerceResponse<T> response) {
    if (product == null) {
      response.addError(ConstantMessage.PRODUCT_DOES_NOT_EXIST);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    return null;
  }

}
