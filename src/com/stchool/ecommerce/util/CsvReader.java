package com.stchool.ecommerce.util;

import com.stchool.ecommerce.model.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public List<Product> getProductsFromCsv() throws FileNotFoundException{
        List<Product> products = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("D:/java-fsd-workspace/Java-Workspace/java-streams"));
            bufferedReader.readLine();

            String productData = bufferedReader.readLine();
            while (productData != null) {
                String[] data = productData.split(",");

                Product product = new Product();
                product.setId(Integer.parseInt(data[0]));
                product.setName(data[1]);
                product.setMaxRetailPrice(Integer.parseInt(data[2]));
                product.setDiscountPercentage(Integer.parseInt(data[3]));
                product.setAvailable(Boolean.parseBoolean(data[4]));
                product.setCompany(data[5]);
                product.setCategory(data[6]);
                product.setManufacturedYear(Integer.parseInt(data[7]));

                products.add(product);
                productData = bufferedReader.readLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return products;
    }

}
