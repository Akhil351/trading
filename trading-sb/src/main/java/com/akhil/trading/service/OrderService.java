package com.akhil.trading.service;

import com.akhil.trading.domain.OrderType;
import com.akhil.trading.model.Coin;
import com.akhil.trading.model.Order;
import com.akhil.trading.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService{
    Order createOrder(Long  userId, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long orderId);
    List<Order> getAllOrderOfUser(Long userId,String orderType,String assetSymbol);
    Order processOrder(Coin coin, BigDecimal quantity, OrderType orderType, Long userId);

}
