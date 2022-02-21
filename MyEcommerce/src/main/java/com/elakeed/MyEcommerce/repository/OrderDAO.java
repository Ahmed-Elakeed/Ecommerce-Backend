package com.elakeed.MyEcommerce.repository;

import com.elakeed.MyEcommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {
    List<Order> findOrdersByEmailOrPhone(String email, String phone);
}
