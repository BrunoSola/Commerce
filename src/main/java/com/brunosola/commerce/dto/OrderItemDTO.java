package com.brunosola.commerce.dto;

import com.brunosola.commerce.entities.OrderItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderItemDTO {

    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;
    private String imgUrl;

    public OrderItemDTO(Long productId, String name, Double price, Integer quantity, String imgUrl) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imgUrl = imgUrl;
    }

    public OrderItemDTO(OrderItem entity) {
        productId = entity.getProduct().getId();
        name = entity.getProduct().getName();
        price = entity.getPrice();
        quantity = entity.getQuantity();
        imgUrl = entity.getProduct().getImgUrl();
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public Double getSubTotal(){
        //return Math.round((price*quantity)*100)/100.0;
        BigDecimal bdPrice = new BigDecimal(Double.toString(price));
        BigDecimal bdQuantity = new BigDecimal(Integer.toString(quantity));
        BigDecimal bdTotal = bdPrice.multiply(bdQuantity).setScale(2, RoundingMode.UNNECESSARY);
        return bdTotal.doubleValue();
    }
}
