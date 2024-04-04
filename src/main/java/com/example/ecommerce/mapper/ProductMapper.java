package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.ProductRequestDto;
import com.example.ecommerce.dto.ProductResponseDto;
import com.example.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import javax.imageio.ImageIO;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        imports = {Math.class, ImageIO.class, LocalDateTime.class, ZoneOffset.class})
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  Product mapProductRequestDtoToProduct(ProductRequestDto productDto);

  @Mapping(target = "productId", expression = "java(product.getId())")
  ProductResponseDto mapProductToProductResponseDto(Product product);

  @Mapping(target = "id",
          expression = "java(savedProduct.getId())" )
  @Mapping(target = "name",
          expression = "java(productDto.getName() == null ? " +
                  "savedProduct.getName() : productDto.getName())")
  @Mapping(target = "description",
          expression = "java(productDto.getDescription() == null ? " +
                  "savedProduct.getDescription() : productDto.getDescription())")
  @Mapping(target = "price",
          expression = "java(productDto.getPrice() == null ? " +
                  "savedProduct.getPrice() : productDto.getPrice())")
  @Mapping(target = "sku",
          expression = "java(productDto.getSku() == null ? " +
                  "savedProduct.getSku() : productDto.getSku())")
  @Mapping(target = "imageLink",
          expression = "java(productDto.getImageLink() == null ? " +
                  "savedProduct.getImageLink() : productDto.getImageLink())")
  @Mapping(target = "stockQuantity",
          expression = "java(productDto.getStockQuantity() == null ? " +
                  "savedProduct.getStockQuantity() : productDto.getStockQuantity())")
  Product mapToUpdateProduct(ProductRequestDto productDto, Product savedProduct);

}
