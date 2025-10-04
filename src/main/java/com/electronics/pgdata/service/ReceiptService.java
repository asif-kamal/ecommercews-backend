package com.electronics.pgdata.service;

import com.electronics.pgdata.dto.ReceiptDTO;
import com.electronics.pgdata.dto.ReceiptItemDTO;
import com.electronics.pgdata.model.Receipt;
import com.electronics.pgdata.model.ReceiptItem;
import com.electronics.pgdata.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Receipt createReceipt(ReceiptDTO receiptDTO) {
        try {
            System.out.println("Creating receipt for user: " + receiptDTO.getUserEmail());

            // Create receipt entity
            Receipt receipt = new Receipt();
            receipt.setReceiptUuid(UUID.randomUUID().toString());
            receipt.setUserEmail(receiptDTO.getUserEmail());
            receipt.setTotalAmount(receiptDTO.getTotal());

            // Calculate subtotal and tax (assuming 8.25% tax rate)
            BigDecimal taxRate = new BigDecimal("0.0825");
            BigDecimal subtotal = receiptDTO.getTotal().divide(BigDecimal.ONE.add(taxRate), 2, RoundingMode.HALF_UP);
            BigDecimal taxAmount = receiptDTO.getTotal().subtract(subtotal);

            receipt.setSubtotal(subtotal);
            receipt.setTaxAmount(taxAmount);

            // Save receipt first to get ID
            receipt = receiptRepository.save(receipt);
            System.out.println("Receipt saved with UUID: " + receipt.getReceiptUuid());

            // Create receipt items
            List<ReceiptItem> receiptItems = receiptDTO.getItems().stream()
                    .map(itemDTO -> new ReceiptItem(
                            receipt,
                            itemDTO.getProductName(),
                            itemDTO.getProductId(),
                            itemDTO.getQuantity(),
                            itemDTO.getPrice(),
                            itemDTO.getSubtotal()
                    ))
                    .collect(Collectors.toList());

            receipt.setReceiptItems(receiptItems);
            receipt = receiptRepository.save(receipt);

            // Send email receipt
            try {
                emailService.sendReceiptEmail(receipt);
                System.out.println("Receipt email sent to: " + receiptDTO.getUserEmail());
            } catch (Exception e) {
                System.err.println("Failed to send receipt email: " + e.getMessage());
                // Don't fail the transaction if email fails
            }

            return receipt;
        } catch (Exception e) {
            System.err.println("Error creating receipt: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create receipt", e);
        }
    }


    public List<Receipt> getUserReceipts(String userEmail) {
        return receiptRepository.findByUserEmailOrderByCreatedOnDesc(userEmail);
    }

    public Receipt getReceiptByUuid(String receiptUuid) {
        return receiptRepository.findByReceiptUuid(receiptUuid);
    }
}
