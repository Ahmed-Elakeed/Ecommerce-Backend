package com.elakeed.MyEcommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderNumber;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, unique = true)
    @Size(min = 11,max = 11)
    String phone;

    @Column(nullable = false, unique = true)
    @NotNull
    String email;

    @Column(nullable = false)
    String address;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    Date orderDate;


    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail> orderDetailList;


    public Order() {

    }

    public Order(String name, String phone, String email, String address, Date orderDate) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.orderDate = orderDate;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber='" + orderNumber + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
