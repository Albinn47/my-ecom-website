package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Order;
import com.ecommerce.project.payload.*;
import com.ecommerce.project.security.services.UserDetailsImpl;
import com.ecommerce.project.service.OrderService;
import com.ecommerce.project.service.StripeService;
import com.ecommerce.project.util.AuthUtil;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private AuthUtil authUtil;
    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod,
                                                  @RequestBody OrderRequestDTO orderRequestDTO){
        String emailId = authUtil.loggedInEmail();
        OrderDTO order = orderService.placeOrder(emailId,orderRequestDTO.getAddressId(),
                paymentMethod,orderRequestDTO.getPgName(),
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgStatus(),
                orderRequestDTO.getPgResponseMessage());
        return new ResponseEntity<OrderDTO>(order, HttpStatus.CREATED);
    }

    @PostMapping("/order/stripe-client-secret")
    public ResponseEntity<String> createStripeClientServer(@RequestBody StripePaymentDTO stripePaymentDTO) throws StripeException {
        System.out.println("Stripe Payment DTO received: "+stripePaymentDTO);
        PaymentIntent paymentIntent = stripeService.paymentIntent(stripePaymentDTO);
        return new ResponseEntity<>(paymentIntent.getClientSecret(),HttpStatus.CREATED);

    }

    @GetMapping("/admin/orders")
    public ResponseEntity<OrderResponse> getAllOrder(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_ORDERS_BY, required = false) String sortBy,
            @RequestParam(name = "SortOrder", defaultValue = AppConstants.SORT_DIRECTION,required = false)String sortOrder
    ){
        OrderResponse orderResponse = orderService.getAllOrders(pageNumber, pageSize, sortBy,sortOrder);
        return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK);

    }

    @PutMapping("/admin/orders/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId,
                                                      @RequestBody OrderStatusUpdateDTO orderStatusUpdateDTO){
        OrderDTO order = orderService.updateOrder(orderId, orderStatusUpdateDTO.getStatus());
        return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);
    }

    @GetMapping("/seller/orders")
    public ResponseEntity<OrderResponse> getAllSellerOrder(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_ORDERS_BY, required = false) String sortBy,
            @RequestParam(name = "SortOrder", defaultValue = AppConstants.SORT_DIRECTION,required = false)String sortOrder
    ){
        OrderResponse orderResponse = orderService.getAllSellerOrders(pageNumber, pageSize, sortBy,sortOrder);
        return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK);

    }

    @PutMapping("/seller/orders/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatusSeller(@PathVariable Long orderId,
                                                      @RequestBody OrderStatusUpdateDTO orderStatusUpdateDTO){
        OrderDTO order = orderService.updateOrder(orderId, orderStatusUpdateDTO.getStatus());
        return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);
    }
}
