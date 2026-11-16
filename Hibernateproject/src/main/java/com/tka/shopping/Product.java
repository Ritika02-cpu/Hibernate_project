
package com.tka.shopping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private double price;

    @Column(name = "brand")
    private String brand;

    @Column(name = "stock")
    private int stock;

    public Product() {
    }

    public Product(int productId, String productName,
            String category, double price, String brand, int stock) {

        super();
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.brand = brand;
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId
                + ", productName=" + productName
                + ", category=" + category
                + ", price=" + price
                + ", brand=" + brand
                + ", stock=" + stock + "]";
    }
}
