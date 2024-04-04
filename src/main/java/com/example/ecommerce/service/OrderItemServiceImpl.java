package com.example.ecommerce.service;

import com.example.ecommerce.constant.ConstantMessage;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.mapper.OrderItemMapper;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
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
public class OrderItemServiceImpl implements OrderItemService {

  private final OrderItemRepository orderItemRepository;
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final OrderItemMapper orderItemMapper;
  private EcommerceResponse<OrderItemResponseDto> response;
  private EcommerceResponse<ListOfOrderItemResponseDto> listOfOrderItemResponse;

  @Override
  @Transactional
  public ResponseEntity<EcommerceResponse<OrderItemResponseDto>> createOrderItem(OrderItemRequestDto orderItemDto) {
    response = new EcommerceResponse<>();
    Long productId = orderItemDto.getProductId();
    Long orderId = orderItemDto.getOrderId();
    ResponseEntity<EcommerceResponse<OrderItemResponseDto>> existenceResponse = validateProductAndOrderExistence(response,
            productId, orderId);
    if(existenceResponse != null) {
      return existenceResponse;
    }
    Product product = productRepository.findById(productId).orElse(null);
    Order order = orderRepository.findById(orderId).orElse(null);
    if (product.getStockQuantity() < orderItemDto.getQuantity()) {
      response.addError(ConstantMessage.INSUFFICIENT_QUANTITY);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    product.setStockQuantity(product.getStockQuantity() - orderItemDto.getQuantity());
    productRepository.save(product);
    OrderItem savedOrderItem = orderItemRepository.save(orderItemMapper.mapOrderItemRequestDtoToOrderItem(orderItemDto,
            product, order));
    response.setPayload(orderItemMapper.mapOrderItemToOrderItemResponseDto(savedOrderItem));
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<EcommerceResponse<OrderItemResponseDto>> getOrderItem(Long orderItemId) {
    response = new EcommerceResponse<>();
    OrderItem orderItem = orderItemRepository.findById(orderItemId).orElse(null);
    ResponseEntity<EcommerceResponse<OrderItemResponseDto>> orderItemResponse =
            checkOrderItemExistence(orderItem, response);
    if(orderItemResponse != null) {
      return orderItemResponse;
    }
    response.setPayload(orderItemMapper.mapOrderItemToOrderItemResponseDto(orderItem));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<EcommerceResponse<ListOfOrderItemResponseDto>> getOrderItems() {
    listOfOrderItemResponse = new EcommerceResponse<>();
    listOfOrderItemResponse.setPayload(getAllOrderItemsMappedWithOrderItemsResponseDto());
    return new ResponseEntity<>(listOfOrderItemResponse, HttpStatus.OK);  }

  @Override
  @Transactional
  public ResponseEntity<EcommerceResponse<ListOfOrderItemResponseDto>> deleteOrderItem(Long orderItemId) {
    listOfOrderItemResponse = new EcommerceResponse<>();
    OrderItem orderItem = orderItemRepository.findById(orderItemId).orElse(null);
    ResponseEntity<EcommerceResponse<ListOfOrderItemResponseDto>> orderItemResponse =
            checkOrderItemExistence(orderItem, listOfOrderItemResponse);
    if(orderItemResponse != null) {
      return orderItemResponse;
    }
    orderItemRepository.deleteById(orderItemId);
    listOfOrderItemResponse.setPayload(getAllOrderItemsMappedWithOrderItemsResponseDto());
    return new ResponseEntity<>(listOfOrderItemResponse, HttpStatus.OK);  }

  @Override
  @Transactional
  public ResponseEntity<EcommerceResponse<OrderItemResponseDto>> updateOrderItem(Long orderItemId, OrderItemRequestDto orderItemDto) {
    response = new EcommerceResponse<>();
    Long productId = orderItemDto.getProductId();
    Long orderId = orderItemDto.getOrderId();
    ResponseEntity<EcommerceResponse<OrderItemResponseDto>> existenceResponse = validateProductAndOrderExistence(response,
            productId, orderId);
    if(existenceResponse != null) {
      return existenceResponse;
    }
    Product product = productRepository.findById(productId).orElse(null);
    Order order = orderRepository.findById(orderId).orElse(null);
    if (product.getStockQuantity() < orderItemDto.getQuantity()) {
      response.addError(ConstantMessage.INSUFFICIENT_QUANTITY);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    product.setStockQuantity(product.getStockQuantity() - orderItemDto.getQuantity());
    productRepository.save(product);
    OrderItem orderItem = orderItemRepository.findById(orderItemId).orElse(null);
    ResponseEntity<EcommerceResponse<OrderItemResponseDto>> orderItemResponse = checkOrderItemExistence(orderItem, response);
    if(orderItemResponse != null) {
      return orderItemResponse;
    }
    OrderItem updateOrderItem = orderItemRepository.save(orderItemMapper.mapToUpdateOrderItem(orderItemDto,
            orderItem, product, order));
    response.setPayload(orderItemMapper.mapOrderItemToOrderItemResponseDto(updateOrderItem));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private ListOfOrderItemResponseDto getAllOrderItemsMappedWithOrderItemsResponseDto() {
    ListOfOrderItemResponseDto orderItemResponse = new ListOfOrderItemResponseDto();
    List<OrderItem> orderItems = orderItemRepository.findAll();
    List<OrderItemResponseDto> getAllOrderItems = orderItems
            .stream()
            .map(orderItemMapper::mapOrderItemToOrderItemResponseDto)
            .sorted(Comparator.comparing(OrderItemResponseDto::getOrderItemId))
            .toList();
    orderItemResponse.setOrderItems(getAllOrderItems);
    return orderItemResponse;
  }

  private <T> ResponseEntity<EcommerceResponse<T>> checkOrderItemExistence(OrderItem orderItem, EcommerceResponse<T> response) {
    if (orderItem == null) {
      response.addError(ConstantMessage.ORDER_ITEM_DOES_NOT_EXIST);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    return null;
  }

  private <T> ResponseEntity<EcommerceResponse<T>> validateProductAndOrderExistence(EcommerceResponse<T> response, Long productId,
                                                                                    Long orderId) {
    if (productId != null && !productRepository.existsById(productId)) {
      response.addError(ConstantMessage.PRODUCT_DOES_NOT_EXIST);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    else if (orderId != null && !orderRepository.existsById(orderId)) {
      response.addError(ConstantMessage.ORDER_DOES_NOT_EXIST);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    return null;
  }

}
