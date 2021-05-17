package com.example;

import javax.persistence.*;

@Entity
@Table(name = "products")
@NamedQuery(name = "discounted-products", query = "select product from com.example.Product product where product.discounted IS NOT NULL")
public class Product {

  @Id
  @GeneratedValue
  private int id;

  private String name;

  private Integer price;

  private Integer discounted;

  // Getters and setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Integer getDiscounted() {
    return discounted;
  }

  public void setDiscounted(Integer discounted) {
    this.discounted = discounted;
  }
}
