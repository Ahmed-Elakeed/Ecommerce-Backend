package com.elakeed.MyEcommerce.controller;

import com.elakeed.MyEcommerce.model.Product;
import com.elakeed.MyEcommerce.repository.ProductDAO;
import com.elakeed.MyEcommerce.repository.ProductTypeDAO;
import com.elakeed.MyEcommerce.repository.TagNameDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/product")
public class ProductController {
    private ProductDAO productDAO;
    private ProductTypeDAO productTypeDAO;
    private TagNameDAO tagNameDAO;

    @Autowired
    public ProductController(ProductDAO productDAO, ProductTypeDAO productTypeDAO, TagNameDAO tagNameDAO) {
        this.productDAO = productDAO;
        this.productTypeDAO = productTypeDAO;
        this.tagNameDAO = tagNameDAO;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(productDAO.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productDAO.findById(id).orElse(null);
        if (product != null) {
            return ResponseEntity.ok().body(product);
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        if (tagNameDAO.findById(product.getTagName().getTagNameId()).orElse(null) == null
                || productTypeDAO.findById(product.getProductType().getProductTypeId()).orElse(null) == null) {
            return ResponseEntity.badRequest().header("Message", "Tag name or Product type not found").build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productDAO.save(product).getProductId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Product updatedProduct = productDAO.findById(id).orElse(null);
        if (updatedProduct != null) {
            if (tagNameDAO.findById(product.getTagName().getTagNameId()).orElse(null) == null
                    || productTypeDAO.findById(product.getProductType().getProductTypeId()).orElse(null) == null) {
                return ResponseEntity.badRequest().header("Message", "Tag name or Product type not found").build();
            }
            updatedProduct.setProductType(product.getProductType());
            updatedProduct.setAvailable(product.getAvailable());
            updatedProduct.setProductColor(product.getProductColor());
            updatedProduct.setProductPrice(product.getProductPrice());
            updatedProduct.setProductName(product.getProductName());
            updatedProduct.setTagName(product.getTagName());
            return ResponseEntity.ok().body(updatedProduct);
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id) {
        boolean isFound = productDAO.findById(id).orElse(null) == null ? false : true;
        if (isFound) {
            productDAO.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }
}
