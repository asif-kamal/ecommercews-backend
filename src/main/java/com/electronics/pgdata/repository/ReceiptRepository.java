package com.electronics.pgdata.repository;

import com.electronics.pgdata.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findByUserEmailOrderByCreatedOnDesc(String userEmail);
    Receipt findByReceiptUuid(String receiptUuid);
}
