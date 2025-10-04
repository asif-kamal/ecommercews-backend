package com.electronics.pgdata.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiptItemDTO {
    private String productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;

    // Constructors
    public ReceiptItemDTO() {}

    public ReceiptItemDTO(String productId, String productName,
                          Integer quantity, BigDecimal price, BigDecimal subtotal) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }
}
