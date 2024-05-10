package com.sii.app.service;

import com.sii.app.model.Product;
import com.sii.app.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ApiService<Product> {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product create(Product entity) {
        if (entity == null) throw new InvalidParameterException("Passed parameter is invalid");
        if (checkIfExist(entity)) throw new EntityExistsException("Product already in database");
        if (entity.getName().isEmpty() || entity.getCurrency().isEmpty() || Double.isNaN(entity.getPrice()))
            throw new InvalidParameterException("At least one of the Product parameters are invalid");
        return productRepository.save(entity);
    }

    @Override
    public Product update(Product entity, Long id) {
        if (entity == null) throw new InvalidParameterException("Passed product is invalid");
        if (id <= 0) throw new InvalidParameterException("Passed id is invalid.");
        if (checkIfExist(entity)) throw new EntityExistsException("Product already in database");
        if (entity.getName().isEmpty() || entity.getCurrency().isEmpty() || Double.isNaN(entity.getPrice()))
            throw new InvalidParameterException("At least one of the Product parameters are invalid");
        Product productToChange = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with given id was not found in the database."));
        productToChange.setCurrency(entity.getCurrency());
        productToChange.setName(entity.getName());
        productToChange.setPrice(entity.getPrice());
        productToChange.setDescription(entity.getDescription());
        return productRepository.save(productToChange);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getOneById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new EntityNotFoundException("Product not found in database.");
        return product.get();
    }

    @Override
    public boolean checkIfExist(Long id) {
        return productRepository.findAll().stream().filter(product -> product.getProductId().equals(id)).toList().size() == 1;
    }

    @Override
    public boolean checkIfExist(Product entity) {
        Iterable<Product> allPlayers = productRepository.findAll();

        for (Product p : allPlayers) {
            if (entity.equals(p)) {
                return true;
            }
        }
        return false;
    }
}
