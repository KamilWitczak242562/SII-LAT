package com.sii.app.service;

import com.sii.app.model.Product;
import com.sii.app.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private AutoCloseable autoCloseable;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createProduct_WithValidInput_ReturnsProduct() {
        //given
        Product product = new Product("Car", "Very good car", 4999.99, "USD");

        //when
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product createdProduct = productService.create(product);

        //then
        assertNotNull(createdProduct);
        assertEquals("Car", createdProduct.getName());
        assertEquals("Very good car", createdProduct.getDescription());
        assertEquals(4999.99, createdProduct.getPrice());
        assertEquals("USD", createdProduct.getCurrency());
    }

    @Test
    void createProduct_WithNull_ThrowsException() {
        //given
        Product product = null;

        //when & then
        assertThrows(InvalidParameterException.class, () -> productService.create(product));
    }

    @Test
    void createProduct_ThatAlreadyExists_ThrowsException() {
        //given
        Product product = new Product("Car", "Very good car", 4999.99, "USD");

        //when
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        //then
        assertThrows(EntityExistsException.class, () -> productService.create(product));
    }

    @Test
    void createProduct_WithInvalidName_ThrowsException() {
        // given
        Product product = new Product("", "Valid", 12.22, "USD");

        //when & then
        assertThrows(InvalidParameterException.class, () -> productService.create(product));
    }

    @Test
    void createProduct_WithInvalidPrice_ThrowsException() {
        // given
        Product product = new Product("Name", "", Double.NaN, "PLN");

        //when & then
        assertThrows(InvalidParameterException.class, () -> productService.create(product));
    }

    @Test
    void createProduct_WithInvalidCurrency_ThrowsException() {
        // given
        Product product = new Product("Name", "", 20.12, "");

        //when & then
        assertThrows(InvalidParameterException.class, () -> productService.create(product));
    }

    @Test
    void updateProduct_WithValidInput_ReturnsUpdatedProduct() {
        // given
        long id = 1;
        Product existingProduct = new Product("Old", "Existing Product", 50.0, "USD");
        Product updatedProduct = new Product("New", "Updated Product", 75.00, "EUR");

        // when
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenReturn(updatedProduct);
        Product result = productService.update(updatedProduct, id);

        // then
        assertEquals(updatedProduct, result);
        assertEquals("New", result.getName());
        assertEquals("EUR", result.getCurrency());
        assertEquals(75.0, result.getPrice());

        verify(productRepository).findById(id);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void getOneById_WithValidId_ReturnsProduct() {
        //given
        Product product = new Product("Car", "Very good car", 4999.99, "USD");
        long id = 1;
        //when
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Product foundProduct = productService.getOneById(id);
        //then
        assertNotNull(foundProduct);
        assertEquals("Car", foundProduct.getName());
        assertEquals("Very good car", foundProduct.getDescription());
        assertEquals(4999.99, foundProduct.getPrice());
        assertEquals("USD", foundProduct.getCurrency());
    }

    @Test
    void getOneById_WithInvalidId_ReturnsProduct() {
        //given
        long invalidId = 999999;
        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());

        //when & then
        assertThrows(EntityNotFoundException.class, () -> productService.getOneById(invalidId));
    }

    @Test
    void updateProduct_WithNullProduct_ThrowsException() {
        // given
        long id = 1;
        Product nullProduct = null;

        // then & when
        assertThrows(InvalidParameterException.class, () -> productService.update(nullProduct, id));
    }

    @Test
    void updateProduct_WithInvalidId_ThrowsException() {
        // given
        long invalidId = -1;
        Product product = new Product("New", "Product Name", 10.0, "USD");

        // then & when
        assertThrows(InvalidParameterException.class, () -> productService.update(product, invalidId));
    }

    @Test
    void updateProduct_WithNonExistingProduct_ThrowsException() {
        // given
        long id = 1;
        Product updatedProduct = new Product("New", "Updated Product", 75.0, "EUR");
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // then & when
        assertThrows(EntityNotFoundException.class, () -> productService.update(updatedProduct, id));

        verify(productRepository).findById(id);
    }

    @Test
    void updateProduct_WithInvalidParameters_ThrowsException() {
        // given
        long id = 1;
        Product existingProduct = new Product("Old", "Existing Product", 50.0, "USD");
        Product invalidProduct = new Product("", "", Double.NaN, "");
        when(productRepository.findAll()).thenReturn(Collections.singletonList(existingProduct));

        // then & when
        assertThrows(InvalidParameterException.class, () -> productService.update(invalidProduct, id));

        verify(productRepository, never()).save(invalidProduct);
    }

    @Test
    void getAll_ReturnsList() {
        //given
        Product existingProduct = new Product("Old", "Existing Product", 50.0, "USD");
        Product updatedProduct = new Product("New", "Updated Product", 75.00, "EUR");
        List<Product> productList = new ArrayList<>();
        productList.add(existingProduct);
        productList.add(updatedProduct);
        //when
        when(productRepository.findAll()).thenReturn(productList);
        List<Product> products = productService.getAll();

        //then
        assertEquals(products.size(), productList.size());
        assertEquals(products.get(0), productList.get(0));
        assertEquals(products.get(1), productList.get(1));

        verify(productRepository).findAll();
    }

}