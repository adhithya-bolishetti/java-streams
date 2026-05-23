package com.stchool.ecommerce.repository;

import com.stchool.ecommerce.model.Product;
import com.stchool.ecommerce.util.CsvReader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private final CsvReader csvReader;
    private final List<Product> products;

    public ProductRepository(CsvReader csvReader) throws FileNotFoundException {
        this.csvReader = csvReader;
        this.products = this.csvReader.getProductsFromCsv();
    }

    public List<Product> findAll() {
        return products;
    }

    public Product save(Product product) {
        this.products.add(product);
        return product;
    }

    public Optional<Product> findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Product update(Product product) {
        return products.stream()
                .filter(p -> p.getId() == product.getId())
                .findFirst()
                .map(p -> {
                    p.setName(product.getName());
                    p.setMaxRetailPrice(product.getMaxRetailPrice());
                    p.setDiscountPercentage(product.getDiscountPercentage());
                    p.setAvailable(product.isAvailable());
                    p.setCompany(product.getCompany());
                    p.setCategory(product.getCategory());
                    p.setManufacturedYear(product.getManufacturedYear());
                    return p;
                })
                .orElse(null);
    }

    public boolean delete(int id) {
        return products.removeIf(p -> p.getId() == id);
    }
}
