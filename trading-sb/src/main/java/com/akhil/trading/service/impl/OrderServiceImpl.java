package com.akhil.trading.service.impl;

import com.akhil.trading.domain.OrderStatus;
import com.akhil.trading.domain.OrderType;
import com.akhil.trading.exception.ApiException;
import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.*;
import com.akhil.trading.repo.OrderItemRepo;
import com.akhil.trading.repo.OrderRepo;
import com.akhil.trading.service.AssetService;
import com.akhil.trading.service.CoinService;
import com.akhil.trading.service.OrderService;
import com.akhil.trading.service.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private WalletService walletService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private AssetService assetService;
    @Override
    public Order createOrder(Long userId, OrderItem orderItem, OrderType orderType) {
        BigDecimal price=orderItem.getQuantity().multiply(BigDecimal.valueOf(coinService.findById(orderItem.getCoinId()).getCurrentPrice()));
        Order order=new Order();
        order.setUserId(userId);
        order.setOrderItemId(orderItem.getId());
        order.setOrderType(orderType);
        order.setPrice(price);
        order.setTimeStamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderRepo.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("order not found"));
    }

    @Override
    public List<Order>  getAllOrderOfUser(Long userId, String orderType, String assetSymbol) {
        return orderRepo.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin,BigDecimal quantity,BigDecimal buyPrice,BigDecimal sellPrice){
        OrderItem orderItem=new OrderItem();
        orderItem.setCoinId(coin.getId());
        orderItem.setQuantity(quantity);
        orderItem.setBuyingPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepo.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Coin coin,BigDecimal quantity,Long userId){
        if(quantity.compareTo(BigDecimal.ZERO) <= 0){
            throw new ApiException("quantity should be greater than zero");
        }
        BigDecimal buyPrice=BigDecimal.valueOf(coin.getCurrentPrice());
        OrderItem orderItem=createOrderItem(coin,quantity,buyPrice,BigDecimal.ZERO);
        Order order=createOrder(userId,orderItem,OrderType.BUY);
        orderItem.setOrderId(order.getId());
        orderItemRepo.save(orderItem);
        walletService.payOrderPayment(order,userId);
        order.setStatus(OrderStatus.SUCCESS);
        Order saveOrder=orderRepo.save(order);
        // create asset
        Asset oldAsset=assetService.findAssetByUserIdAndCoinId(order.getUserId(),orderItem.getCoinId());
        if (oldAsset==null) {
            assetService.createAsset(userId, coin, orderItem.getQuantity());
        } else{
            assetService.updateAsset(oldAsset.getId(),quantity);
        }

        return saveOrder;

    }


    @Transactional
    public Order sellAsset(Coin coin,BigDecimal quantity,Long userId){
        if(quantity.compareTo(BigDecimal.ZERO) <= 0){
            throw new ApiException("quantity should be greater than zero");
        }
        BigDecimal sellPrice=BigDecimal.valueOf(coin.getCurrentPrice());
        Asset assetToSell=assetService.findAssetByUserIdAndCoinId(userId,coin.getId());
        if (assetToSell!=null){
            OrderItem orderItem=createOrderItem(coin,quantity,assetToSell.getBuyPrice() ,sellPrice);
            Order order=createOrder(userId,orderItem,OrderType.SELL);
            orderItem.setOrderId(order.getId());
            orderItemRepo.save(orderItem);
            if(assetToSell.getQuantity().compareTo(quantity) >= 0){
                order.setStatus(OrderStatus.SUCCESS);
                Order saveOrder=orderRepo.save(order);
                walletService.payOrderPayment(order,userId);
                Asset updatedAsset=assetService.updateAsset(assetToSell.getId(),quantity.multiply(BigDecimal.valueOf(-1)));
                if(updatedAsset.getQuantity().multiply(BigDecimal.valueOf(coin.getCurrentPrice())).compareTo(BigDecimal.ONE) <= 0){
                    assetService.deleteAsset(updatedAsset.getId());
                }
                return saveOrder;

            }
            else{
                throw new ApiException("Insufficient quantity to sell");
            }

        }
        throw  new ApiException("Asset not found for selling");




    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, BigDecimal quantity, OrderType orderType, Long userId) {
        if(orderType.equals(OrderType.BUY)){
            return buyAsset(coin,quantity,userId);
        } else if(orderType.equals(OrderType.SELL)){
            return sellAsset(coin,quantity,userId);
        }
        throw new ApiException("invalid order type");
    }
}
