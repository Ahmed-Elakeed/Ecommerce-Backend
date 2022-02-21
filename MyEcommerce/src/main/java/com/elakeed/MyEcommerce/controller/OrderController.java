package com.elakeed.MyEcommerce.controller;

import com.elakeed.MyEcommerce.model.Order;
import com.elakeed.MyEcommerce.repository.OrderDAO;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/order")
public class OrderController {
    private OrderDAO orderDAO;

    @Autowired
    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok().body(orderDAO.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        Order order = orderDAO.findById(id).orElse(null);
        if (order != null) {
            return ResponseEntity.ok().body(order);
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order) {
        if (orderDAO.findOrdersByEmailOrPhone(order.getEmail(), order.getPhone()).size() == 0) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderDAO.save(order).getOrderNumber()).toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.badRequest().header("Message", "Email or Phone is already exists").build();
    }

    @PutMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id,@Valid @RequestBody Order order) {
        Order updatedOrder = orderDAO.findById(id).orElse(null);
        if (updatedOrder != null) {
            if (orderDAO.findOrdersByEmailOrPhone(order.getEmail(), order.getPhone()).size() != 0) {
                return ResponseEntity.badRequest().header("Message", "Email or Phone is already exists").build();
            }
            updatedOrder.setOrderDate(order.getOrderDate());
            updatedOrder.setAddress(order.getAddress());
            updatedOrder.setEmail(order.getEmail());
            updatedOrder.setName(order.getName());
            updatedOrder.setPhone(order.getPhone());
            return ResponseEntity.ok().body(updatedOrder);
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable("id") Long id) {
        boolean isFound = orderDAO.findById(id).orElse(null) == null ? false : true;
        if (isFound) {
            orderDAO.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }

    @PatchMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<Order> partialUpdate(@PathVariable("id") Long id,@Valid @RequestBody Order order) {
        Order updatedOrder = orderDAO.findById(id).orElse(null);
        if (updatedOrder != null) {
            if (StringUtils.hasLength(order.getEmail()) || StringUtils.hasLength(order.getPhone())) {
                if (orderDAO.findOrdersByEmailOrPhone(order.getEmail(), order.getPhone()).size() != 0) {
                    return ResponseEntity.badRequest().header("Message", "Email or Phone data is already exists").build();
                }
            }
            if (StringUtils.hasLength(order.getAddress())) {
                updatedOrder.setAddress(order.getAddress());
            }
            if (StringUtils.hasLength(order.getEmail())) {
                updatedOrder.setEmail(order.getEmail());
            }
            if (StringUtils.hasLength(order.getPhone())) {
                updatedOrder.setPhone(order.getPhone());
            }
            if (StringUtils.hasLength(order.getName())) {
                updatedOrder.setName(order.getName());
            }
            if (order.getOrderDate()!=null) {
                updatedOrder.setOrderDate(order.getOrderDate());
            }
            return ResponseEntity.ok().body(updatedOrder);
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }
}
