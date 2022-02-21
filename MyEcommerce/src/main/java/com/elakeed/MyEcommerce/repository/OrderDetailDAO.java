package com.elakeed.MyEcommerce.repository;

import com.elakeed.MyEcommerce.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {
}
