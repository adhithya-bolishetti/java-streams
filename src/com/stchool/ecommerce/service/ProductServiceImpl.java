package com.stchool.ecommerce.service;

import com.stchool.ecommerce.exception.ProductNotFoundException;
import com.stchool.ecommerce.model.Product;
import com.stchool.ecommerce.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getProductsByAvailability(boolean isAvailable) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.isAvailable() == isAvailable)
                .toList();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getCategory().equals(category))
                .toList();
    }

    @Override
    public Product getProductById(int id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + id));
    }

    @Override
    public List<String> getAllCategories() {
        return productRepository.findAll()
                .stream().map(Product::getCategory)
                .distinct().toList();
    }

    @Override
    public List<Product> getProductsAbovePrice(double price) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getMaxRetailPrice() > price)
                .toList();
    }

    @Override
    public List<String> getAllProductNames() {
        return productRepository.findAll()
                .stream().map(Product::getName)
                .distinct().toList();
    }

    @Override
    public long getAvailableProductCount() {
        return productRepository.findAll()
                .stream().filter(Product::isAvailable).count();
    }

    @Override
    public boolean existsByCompany(String company) {
        return productRepository.findAll()
                .stream()
                .anyMatch(product -> product.getCompany().equalsIgnoreCase(company));
    }

    @Override
    public boolean areAllProductsAvailable() {
        return productRepository.findAll()
                .stream()
                .allMatch(Product::isAvailable);
    }

    @Override
    public Optional<Product> getFirstProduct() {
        return productRepository.findAll()
                .stream()
                .findFirst();
    }

    @Override
    public List<Product> getTopNExpensiveProducts(int n) {
        return productRepository.findAll()
                .stream()
                .sorted((p1, p2) -> Double.compare(
                        p2.getMaxRetailPrice(), p1.getMaxRetailPrice()))
                .limit(n)
                .toList();
    }

    @Override
    public List<Product> sortProductsByPriceAsc() {
        return productRepository.findAll()
                .stream()
                .sorted((p1, p2) -> Double.compare(
                        p1.getMaxRetailPrice(), p2.getMaxRetailPrice()
                )).toList();
    }

    @Override
    public List<Product> sortProductsByPriceDesc() {
        return productRepository.findAll()
                .stream()
                .sorted((p1, p2) -> Double.compare(
                        p2.getMaxRetailPrice(), p1.getMaxRetailPrice()
                )).toList();
    }

    @Override
    public double getTotalInventoryValue() {
        return productRepository.findAll()
                .stream()
                .mapToDouble(Product::getMaxRetailPrice)
                .sum();
    }

    @Override
    public double getTotalPriceAfterDiscount() {
        return productRepository.findAll()
                .stream()
                .mapToDouble(product ->
                        product.getMaxRetailPrice() -
                                (product.getMaxRetailPrice() * product.getDiscountPercentage() / 100))
                .sum();
    }

    @Override
    public List<Product> getProductsManufacturedAfter(int year) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getManufacturedYear() > year)
                .toList();
    }

    @Override
    public List<Product> getAvailableProductsAbovePrice(double price) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.isAvailable() &&
                        product.getMaxRetailPrice() > price)
                .toList();
    }

    @Override
    public Map<String, Long> getProductCountByCategory() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory, Collectors.counting()
                ));
    }

    @Override
    public Map<String, List<Product>> groupProductsByCategory() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

    @Override
    public Map<String, List<Product>> groupProductsByCompany() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Product::getCompany));
    }

    @Override
    public Map<Boolean, List<Product>> partitionProductsByAvailability() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.partitioningBy(Product::isAvailable));
    }

    @Override
    public Optional<Product> getMostExpensiveProduct() {
        return productRepository.findAll()
                .stream()
                .max(Comparator.comparingDouble(Product::getMaxRetailPrice));
    }

    @Override
    public Optional<Product> getCheapestProduct() {
        return productRepository.findAll()
                .stream()
                .min(Comparator.comparingDouble(Product::getMaxRetailPrice));
    }

    @Override
    public Map<Integer, Product> mapProductsById() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
    }

    @Override
    public Map<String, Double> getAveragePriceByCategory() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.averagingDouble(Product::getMaxRetailPrice)
                ));
    }

    @Override
    public Map<String, List<Product>> getTop3ExpensiveProductsByCategory() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                product -> product.stream()
                                        .sorted(Comparator.comparingDouble(Product::getMaxRetailPrice)
                                                .reversed())
                                        .limit(3)
                                        .toList()
                        )
                ));
    }
}
