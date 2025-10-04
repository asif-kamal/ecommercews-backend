package com.electronics.pgdata.dto;

import java.math.BigDecimal;
import java.util.List;

public class ReceiptDTO {
    private String userEmail;
    private BigDecimal totalAmount;
    private BigDecimal taxAmount;
    private BigDecimal subtotal;
    private List<ReceiptItemDTO> items;

    // Constructors
    public ReceiptDTO() {}

    public ReceiptDTO(String userEmail, BigDecimal totalAmount,
                      BigDecimal taxAmount, BigDecimal subtotal, List<ReceiptItemDTO> items) {
        this.userEmail = userEmail;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.subtotal = subtotal;
        this.items = items;
    }

    // Getters and Setters
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public List<ReceiptItemDTO> getItems() { return items; }
    public void setItems(List<ReceiptItemDTO> items) { this.items = items; }
}
