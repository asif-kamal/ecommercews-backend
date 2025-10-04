package com.electronics.pgdata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;

    @Column(name = "receipt_uuid", unique = true, nullable = false)
    private String receiptUuid;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ReceiptItem> receiptItems;

    // Constructors
    public Receipt() {
        this.createdOn = LocalDateTime.now();
    }

    public Receipt(String receiptUuid, String userEmail, BigDecimal totalAmount) {
        this();
        this.receiptUuid = receiptUuid;
        this.userEmail = userEmail;
        this.totalAmount = totalAmount;
    }


}
