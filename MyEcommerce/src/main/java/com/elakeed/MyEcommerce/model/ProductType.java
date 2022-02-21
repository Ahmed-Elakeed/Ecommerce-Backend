package com.elakeed.MyEcommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products_types")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productTypeId;

    @Column(nullable = false, unique = true)
    String productType;


    @OneToMany(mappedBy = "productType")
    @JsonIgnore
    private List<Product> productList;

    public ProductType() {

    }

    public ProductType(String productType) {
        this.productType = productType;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "productTypeId=" + productTypeId +
                ", productType='" + productType + '\'' +
                '}';
    }
}
