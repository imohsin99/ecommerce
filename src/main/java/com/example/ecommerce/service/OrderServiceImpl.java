package com.example.ecommerce.service;

import com.example.ecommerce.constant.ConstantMessage;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.mapper.OrderMapper;
import com.example.ecommerce.repository.OrderRepository;
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
public class OrderServiceImpl implements OrderService {

  private final OrderMapper orderMapper;
  private final OrderRepository orderRepository;
  private EcommerceResponse<OrderResponseDto> response;
  private EcommerceResponse<ListOfOrderResponseDto> listOfOrderResponse;

  @Override
  @Transactional
  public ResponseEntity<EcommerceResponse<OrderResponseDto>> createOrder() {
    response = new EcommerceResponse<>();
    Order order = new Order();
    Order savedOrder = orderRepository.save(orderMapper.mapToOrder(order));
    response.setPayload(orderMapper.mapOrderToOrderResponseDto(savedOrder));
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<EcommerceResponse<OrderResponseDto>> getOrder(Long orderId) {
    response = new EcommerceResponse<>();
    Order order = orderRepository.findById(orderId).orElse(null);
    ResponseEntity<EcommerceResponse<OrderResponseDto>> orderResponse = checkOrderExistence(order, response);
    if(orderResponse != null) {
      return orderResponse;
    }
    response.setPayload(orderMapper.mapOrderToOrderResponseDto(order));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<EcommerceResponse<ListOfOrderResponseDto>> getOrders() {
    listOfOrderResponse = new EcommerceResponse<>();
    listOfOrderResponse.setPayload(getAllOrdersMappedWithOrderResponseDto());
    return new ResponseEntity<>(listOfOrderResponse, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity<EcommerceResponse<ListOfOrderResponseDto>> deleteOrder(Long orderId) {
    listOfOrderResponse = new EcommerceResponse<>();
    Order order = orderRepository.findById(orderId).orElse(null);
    ResponseEntity<EcommerceResponse<ListOfOrderResponseDto>> orderResponse = checkOrderExistence(order, listOfOrderResponse);
    if(orderResponse != null) {
      return orderResponse;
    }
    orderRepository.deleteById(orderId);
    listOfOrderResponse.setPayload(getAllOrdersMappedWithOrderResponseDto());
    return new ResponseEntity<>(listOfOrderResponse, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity<EcommerceResponse<OrderResponseDto>> updateOrder(Long orderId, OrderRequestDto orderDto) {
    response = new EcommerceResponse<>();
    Order savedOrder = orderRepository.findById(orderId).orElse(null);
    ResponseEntity<EcommerceResponse<OrderResponseDto>> orderResponse = checkOrderExistence(savedOrder, response);
    if(orderResponse != null) {
      return orderResponse;
    }
    Order updateOrder = orderRepository.save(orderMapper.mapToUpdateOrder(orderDto, savedOrder));
    response.setPayload(orderMapper.mapOrderToOrderResponseDto(updateOrder));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private ListOfOrderResponseDto getAllOrdersMappedWithOrderResponseDto() {
    ListOfOrderResponseDto orderResponse = new ListOfOrderResponseDto();
    List<Order> orders = orderRepository.findAll();
    List<OrderResponseDto> getAllOrders = orders
            .stream()
            .map(orderMapper::mapOrderToOrderResponseDto)
            .sorted(Comparator.comparing(OrderResponseDto::getOrderId))
            .toList();
    orderResponse.setOrders(getAllOrders);
    return orderResponse;
  }

  private <T> ResponseEntity<EcommerceResponse<T>> checkOrderExistence(Order order, EcommerceResponse<T> response) {
    if (order == null) {
      response.addError(ConstantMessage.ORDER_DOES_NOT_EXIST);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    return null;
  }

}
