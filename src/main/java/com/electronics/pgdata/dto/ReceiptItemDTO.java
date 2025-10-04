package com.electronics.pgdata.dto;

import java.math.BigDecimal;

public class ReceiptItemDTO {
    private String productName;
    private String productUuid;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    // Constructors
    public ReceiptItemDTO() {}

    public ReceiptItemDTO(String productName, String productUuid,
                          Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
        this.productName = productName;
        this.productUuid = productUuid;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductUuid() { return productUuid; }
    public void setProductUuid(String productUuid) { this.productUuid = productUuid; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}
