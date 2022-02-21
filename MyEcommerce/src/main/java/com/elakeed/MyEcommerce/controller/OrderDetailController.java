package com.elakeed.MyEcommerce.controller;

import com.elakeed.MyEcommerce.model.OrderDetail;
import com.elakeed.MyEcommerce.repository.OrderDAO;
import com.elakeed.MyEcommerce.repository.OrderDetailDAO;
import com.elakeed.MyEcommerce.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/orderDetail")
public class OrderDetailController {
    private OrderDetailDAO orderDetailDAO;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    @Autowired
    public OrderDetailController(OrderDetailDAO orderDetailDAO, OrderDAO orderDAO, ProductDAO productDAO) {
        this.orderDetailDAO = orderDetailDAO;
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
        return ResponseEntity.ok().body(orderDetailDAO.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable("id") Long id) {
        OrderDetail orderDetail = orderDetailDAO.findById(id).orElse(null);
        if (orderDetail != null) {
            return ResponseEntity.ok().body(orderDetail);
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }

    @PostMapping
    public ResponseEntity<OrderDetail> addOrderDetail(@RequestBody OrderDetail orderDetail) {
        if (orderDAO.findById(orderDetail.getOrder().getOrderNumber()).orElse(null) == null
                || productDAO.findById(orderDetail.getProduct().getProductId()).orElse(null) == null) {
            return ResponseEntity.badRequest().header("Message", "Order or Product not found").build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderDetailDAO.save(orderDetail).getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable("id") Long id, @RequestBody OrderDetail orderDetail) {
        OrderDetail updatedOrderDetail = orderDetailDAO.findById(id).orElse(null);
        if (updatedOrderDetail != null) {
            if (orderDAO.findById(orderDetail.getOrder().getOrderNumber()).orElse(null) == null
                    || productDAO.findById(orderDetail.getProduct().getProductId()).orElse(null) == null) {
                return ResponseEntity.badRequest().header("Message", "Order or Product not found").build();
            }
            updatedOrderDetail.setOrder(orderDetail.getOrder());
            updatedOrderDetail.setProduct(orderDetail.getProduct());
            return ResponseEntity.ok().body(updatedOrderDetail);
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteOrderDetailById(@PathVariable("id") Long id) {
        boolean isFound = orderDetailDAO.findById(id).orElse(null) == null ? false : true;
        if (isFound) {
            orderDetailDAO.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }
}
