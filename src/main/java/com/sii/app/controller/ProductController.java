package com.sii.app.controller;

import com.sii.app.model.Product;
import com.sii.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Product newProduct = productService.create(product);
        return ResponseEntity.ok().body(Map.of("message", "Product created successfully.",
                "response", newProduct));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAll();
        return ResponseEntity.ok().body(Map.of("message", "Products got successfully.",
                "response", products));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable Long productId) {
        Product updatedProduct = productService.update(product, productId);
        return ResponseEntity.ok().body(Map.of("message", "Product updated successfully.",
                "response", updatedProduct));
    }

}
