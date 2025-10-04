package com.electronics.pgdata.controller;

import com.electronics.pgdata.dto.ReceiptDTO;
import com.electronics.pgdata.model.Receipt;
import com.electronics.pgdata.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/checkout")
    public ResponseEntity<?> createReceipt(@RequestBody ReceiptDTO receiptDTO, Principal principal) {
        try {
            if (principal == null) {
                System.err.println("No authenticated user found");
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }

            String userEmail = principal.getName();
            System.out.println("Processing checkout for user: " + userEmail);

            // Set the authenticated user's email
            receiptDTO.setUserEmail(userEmail);

            Receipt receipt = receiptService.createReceipt(receiptDTO);

            return new ResponseEntity<>(receipt, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error processing checkout: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Failed to process checkout", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<Receipt>> getUserReceipts(Principal principal) {
        try {
            if (principal == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            String userEmail = principal.getName();
            List<Receipt> receipts = receiptService.getUserReceipts(userEmail);

            return new ResponseEntity<>(receipts, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching user receipts: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{receiptUuid}")
    public ResponseEntity<Receipt> getReceiptByUuid(@PathVariable String receiptUuid, Principal principal) {
        try {
            if (principal == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Receipt receipt = receiptService.getReceiptByUuid(receiptUuid);

            if (receipt == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Verify the receipt belongs to the authenticated user
            String userEmail = principal.getName();
            if (!receipt.getUserEmail().equals(userEmail)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(receipt, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching receipt: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
