package com.elakeed.MyEcommerce.controller;

import com.elakeed.MyEcommerce.model.ProductType;
import com.elakeed.MyEcommerce.repository.ProductTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/productType")
public class ProductTypeController {
    private ProductTypeDAO productTypeDAO;

    @Autowired
    public ProductTypeController(ProductTypeDAO productTypeDAO) {
        this.productTypeDAO = productTypeDAO;
    }

    @GetMapping
    public ResponseEntity<List<ProductType>> getAllProductTypes() {
        return ResponseEntity.ok(productTypeDAO.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductType> getProductTypeById(@PathVariable("id") Long id) {
        ProductType productType = productTypeDAO.findById(id).orElse(null);
        if (productType != null) {
            return ResponseEntity.ok(productType);
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }

    @PostMapping
    public ResponseEntity<ProductType> addProductType(@RequestBody ProductType productType) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productTypeDAO.save(productType).getProductTypeId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<ProductType> updateProductType(@PathVariable("id") Long id, @RequestBody ProductType productType) {
        ProductType updatedProductType = productTypeDAO.findById(id).orElse(null);
        if (updatedProductType != null) {
            updatedProductType.setProductType(productType.getProductType());
            return ResponseEntity.ok(updatedProductType);
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProductTypeById(@PathVariable("id") Long id) {
        ProductType productType = productTypeDAO.findById(id).orElse(null);
        if (productType!=null) {
            if (productType.getProductList().size() == 0) {
                productTypeDAO.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().header("Message", "Can't delete this record because some products depends on it").build();
            }
        }
        return ResponseEntity.notFound().header("Message", "Id Not Found").build();
    }
}
