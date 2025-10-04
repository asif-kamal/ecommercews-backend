package com.electronics.pgdata.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReceiptDTO {
    private String userId;
    private String userEmail;
    private String userName;
    private List<ReceiptItemDTO> items;
    private BigDecimal total;
    private LocalDateTime orderDate;

    // Constructors
    public ReceiptDTO() {}

    public ReceiptDTO(String userId, String userEmail, String userName,
                      List<ReceiptItemDTO> items, BigDecimal total, LocalDateTime orderDate) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.items = items;
        this.total = total;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public List<ReceiptItemDTO> getItems() { return items; }
    public void setItems(List<ReceiptItemDTO> items) { this.items = items; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
}
