package com.bs.messaging.app;

public class ProductBoughtEventMessage extends JsonMessage {

    private final Double price;
    private final String productName;

    public ProductBoughtEventMessage(Double price, String productName) {
        this.price = price;
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "ProductBoughtEventMessage{" +
                "price=" + price +
                ", productName='" + productName + '\'' +
                '}';
    }
}
