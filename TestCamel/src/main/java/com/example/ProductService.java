package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

  private final ProductRepository products;

  @Autowired
  public ProductService(ProductRepository products) {
    this.products = products;
  }

  public Product findById(Integer id) {
    Optional<Product> product = products.findById(id);
    if (!product.isPresent()) {
      throw new IllegalStateException("com.example.Product could not found for given id:" + id);
    }
    return product.get();
  }

  public Iterable<Product> findAll() {
    return products.findAll();
  }

  public void save(Product product) {
    products.save(product);
  }
}
