package com.example;

import javax.persistence.*;

@Entity
@Table(name = "discounts")
public class Discount {

  @Id
  @GeneratedValue
  private int id;

  private Integer amount;

  @OneToOne
  private Product product;

  // Getters and setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
