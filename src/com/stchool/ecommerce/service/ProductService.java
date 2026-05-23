package com.stchool.ecommerce.service;

import com.stchool.ecommerce.exception.ProductNotFoundException;
import com.stchool.ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByAvailability(boolean isAvailable);
    List<Product> getProductsByCategory(String category);
    Product getProductById(int id) throws ProductNotFoundException;
    List<String> getAllCategories();
}
