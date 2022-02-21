package com.elakeed.MyEcommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tag_names")
public class TagName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tagNameId;
    @Column(nullable = false, unique = true)
    String tagName;


    @OneToMany(mappedBy = "tagName")
    @JsonIgnore
    private List<Product> productList;

    public TagName() {

    }

    public TagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getTagNameId() {
        return tagNameId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "TagName{" +
                "tagNameId=" + tagNameId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
