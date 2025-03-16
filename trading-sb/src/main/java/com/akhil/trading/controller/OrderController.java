package com.akhil.trading.controller;



import com.akhil.trading.exception.ApiException;
import com.akhil.trading.model.Coin;
import com.akhil.trading.model.Order;
import com.akhil.trading.model.UserContext;
import com.akhil.trading.request.CreateOrderRequest;
import com.akhil.trading.response.Response;
import com.akhil.trading.service.CoinService;
import com.akhil.trading.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @Autowired
    private CoinService coinService;

    @Autowired
    private UserContext userContext;




    @PostMapping("/pay")
    public ResponseEntity<Response> payOrderPayment(
            @RequestBody CreateOrderRequest req

    ) throws Exception {
        Coin coin =coinService.findById(req.getCoinId());
        Order order = orderService.processOrder(coin,req.getQuantity(),req.getOrderType(), userContext.getId());

        return ResponseEntity.ok(Response.builder().data(order).build());

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Response> getOrderById(
            @PathVariable Long orderId
    )  {
        Order order = orderService.getOrderById(orderId);
        if (order.getUserId().equals(userContext.getId())) {
            return ResponseEntity.ok(Response.builder().data(order).build());
        } else {
           throw new ApiException("you don't have access");
        }
    }

    @GetMapping
    public ResponseEntity<Response> getAllOrdersForUser(
            @RequestParam(required = false) String order_type,
            @RequestParam(required = false) String asset_symbol
    )  {
        List<Order> userOrders = orderService.getAllOrderOfUser(userContext.getId(), order_type,asset_symbol);
        return ResponseEntity.ok(Response.builder().data(userOrders).build());
    }




}

