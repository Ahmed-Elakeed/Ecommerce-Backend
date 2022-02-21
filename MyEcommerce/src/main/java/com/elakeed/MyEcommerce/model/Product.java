package com.elakeed.MyEcommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;

    @Column(nullable = false)
    String productName;
    @Column(nullable = false)
    Integer productPrice;
    @Column(nullable = false)
    String productColor;
    @Column(nullable = false)
    Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "productTypeId")
    ProductType productType;

    @ManyToOne
    @JoinColumn(name = "tagNameId")
    TagName tagName;


    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderDetail> orderDetailList;

    public Product() {

    }

    public Product(String name, Integer price, String color, Boolean isAvailable, ProductType productType, TagName tagName) {
        this.productName = name;
        this.productPrice = price;
        this.productColor = color;
        this.isAvailable = isAvailable;
        this.productType = productType;
        this.tagName = tagName;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer price) {
        this.productPrice = price;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String color) {
        this.productColor = color;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public TagName getTagName() {
        return tagName;
    }

    public void setTagName(TagName tagName) {
        this.tagName = tagName;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + productName + '\'' +
                ", price=" + productPrice +
                ", color='" + productColor + '\'' +
                ", isAvailable=" + isAvailable +
                ", productType=" + productType +
                ", tagName=" + tagName +
                '}';
    }
}
