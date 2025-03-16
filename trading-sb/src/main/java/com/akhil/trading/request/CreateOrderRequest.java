package com.akhil.trading.request;

import com.akhil.trading.domain.OrderType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderRequest {
    private String coinId;
    private BigDecimal quantity;
    private OrderType orderType;
}
