package com.example;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude = WebMvcAutoConfiguration.class)
public class SpringBootCamelApplication {

  @Autowired
  private Environment env;

  public static void main(String[] args) {
    SpringApplication.run(SpringBootCamelApplication.class, args);
  }

  @Component
  class RestApi extends RouteBuilder {

    @Override
    public void configure() {
      restConfiguration()
          .contextPath("/")
          .apiContextPath("/api-doc")
          .apiProperty("api.title", "App Demo")
          .apiProperty("api.version", "1.0")
          .apiProperty("cors", "true")
          .apiContextRouteId("doc-api")
          .port(env.getProperty("server.port", "8080"))
          .bindingMode(RestBindingMode.json);

      rest("/products").description("Details of products")
          .get("/").description("List of all products")
          .route().routeId("products-api")
          .bean(ProductService.class, "findAll")
          .endRest()
          .get("discounts/{id}").description("com.example.Discount of a product")
          .route().routeId("discount-api")
          .bean(DiscountService.class, "findDiscount(${header.id})");
    }
  }

  @Component
  class TimedJobs extends RouteBuilder {

    @Override
    public void configure() {
      from("timer:new-discount?delay=1000&period={{discount.newDiscountPeriod:2000}}")
          .routeId("make-discount")
          .bean("discountService", "makeDiscount")
          .to("jpa:org.apache.camel.example.spring.boot.rest.jpa.com.example.Discount")
          .log("Created %${body.amount} discount for ${body.product.name}");

      from("jpa:org.apache.camel.example.spring.boot.rest.jpa.com.example.Product"
          + "?namedQuery=discounted-products"
          + "&delay={{discount.listDiscountPeriod:6000}}"
          + "&consumeDelete=false")
          .routeId("list-discounted-products")
          .log(
              "Discounted product ${body.name}. Price dropped from ${body.price} to ${body.discounted}");
    }
  }
}