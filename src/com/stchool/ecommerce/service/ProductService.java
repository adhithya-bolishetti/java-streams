package com.stchool.ecommerce.service;

import com.stchool.ecommerce.exception.ProductNotFoundException;
import com.stchool.ecommerce.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    List<Product> getProductsByAvailability(boolean isAvailable);
    List<Product> getProductsByCategory(String category);
    Product getProductById(int id) throws ProductNotFoundException;
    List<String> getAllCategories();
    List<Product> getProductsAbovePrice(double price);
    List<String> getAllProductNames();
    long getAvailableProductCount();
    boolean existsByCompany(String company);
    boolean areAllProductsAvailable();
    Optional<Product> getFirstProduct();
    List<Product> getTopNExpensiveProducts(int n);
    List<Product> sortProductsByPriceAsc();
    List<Product> sortProductsByPriceDesc();
    double getTotalInventoryValue();
    double getTotalPriceAfterDiscount();
    List<Product> getProductsManufacturedAfter(int year);
    List<Product> getAvailableProductsAbovePrice(double price);
    Map<String, Long> getProductCountByCategory();
    Map<String, List<Product>> groupProductsByCategory();
    Map<String, List<Product>> groupProductsByCompany();
    Map<Boolean, List<Product>> partitionProductsByAvailability();
    Optional<Product> getMostExpensiveProduct();
    Optional<Product> getCheapestProduct();
    Map<Integer, Product> mapProductsById();
    Map<String, Double> getAveragePriceByCategory();
    Map<String, List<Product>> getTop3ExpensiveProductsByCategory();
}
