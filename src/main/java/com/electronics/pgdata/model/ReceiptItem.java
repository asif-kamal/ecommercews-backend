package com.electronics.pgdata.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "receipt_items")
public class ReceiptItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_uuid", nullable = false)
    private String productUuid;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    // Constructors
    public ReceiptItem() {}

    public ReceiptItem(Receipt receipt, String productName, String productUuid,
                       Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
        this.receipt = receipt;
        this.productName = productName;
        this.productUuid = productUuid;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
}
