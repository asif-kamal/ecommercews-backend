package com.electronics.pgdata.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
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
}
