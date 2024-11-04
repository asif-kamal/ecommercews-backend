package com.electronics.pgdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.electronics.pgdata.model.Electronic;

@Repository
public interface ElectronicRepository extends JpaRepository<Electronic, String> {
    Page<Electronic> findByBrand(String brand, Pageable pageable);
    List<Electronic> findByPricesAmountMaxLessThan(Double maxPrice);
    
    @Query("SELECT e FROM Electronic e WHERE LOWER(e.primaryCategories) LIKE LOWER(CONCAT('%', :category, '%'))")
    List<Electronic> findByCategory(@Param("category") String category);

    // Alternative method using primaryCategories field
    List<Electronic> findByPrimaryCategories(String primaryCategories);
    
    @Override
    Page<Electronic> findAll(Pageable pageable);
}
