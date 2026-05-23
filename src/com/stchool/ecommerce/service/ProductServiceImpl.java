package com.stchool.ecommerce.service;

import com.stchool.ecommerce.exception.ProductNotFoundException;
import com.stchool.ecommerce.model.Product;
import com.stchool.ecommerce.repository.ProductRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getProductsByAvailability(boolean isAvailable) {
        return productRepository.findAll().stream().filter(product -> product.isAvailable() == isAvailable).toList();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findAll().stream().filter(product -> product.getCategory().equals(category)).toList();
    }

    @Override
    public Product getProductById(int id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + id));
    }

    @Override
    public List<String> getAllCategories() {
        return productRepository.findAll().stream().map(Product::getCategory).distinct().toList();
    }
}
