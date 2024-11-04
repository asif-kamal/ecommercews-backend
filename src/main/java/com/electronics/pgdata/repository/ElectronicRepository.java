package com.electronics.pgdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.electronics.pgdata.model.Electronic;

public interface ElectronicRepository extends JpaRepository<Electronic, Long> {
    List<Electronic> findByBrand(String brand);
    List<Electronic> findByPricesAmountMaxLessThan(Double maxPrice);
    
    @Query("SELECT e FROM Electronic e WHERE :category = ANY(e.categories)")
    List<Electronic> findByCategory(@Param("category") String category);
}
