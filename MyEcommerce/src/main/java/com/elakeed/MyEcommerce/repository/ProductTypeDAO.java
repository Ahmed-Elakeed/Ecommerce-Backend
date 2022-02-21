package com.elakeed.MyEcommerce.repository;

import com.elakeed.MyEcommerce.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeDAO extends JpaRepository<ProductType, Long> {
}
