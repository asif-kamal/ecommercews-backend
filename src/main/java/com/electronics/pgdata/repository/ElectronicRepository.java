package com.electronics.pgdata.repository;

import java.math.BigDecimal;
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

    // Fixed search query - make sure it matches your entity fields exactly
    @Query("SELECT e FROM Electronic e WHERE " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.brand) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.category) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Electronic> searchByNameOrDescriptionOrBrand(@Param("query") String query, Pageable pageable);

    // Alternative simple search method for testing
    @Query("SELECT e FROM Electronic e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Electronic> searchByNameOnly(@Param("query") String query, Pageable pageable);

    // Random electronics query
    @Query(value = "SELECT * FROM electronics ORDER BY RANDOM() LIMIT :limit",
            nativeQuery = true)
    List<Electronic> findRandomElectronics(@Param("limit") int limit);

    // Find by brand
    Page<Electronic> findByBrandIgnoreCase(String brand, Pageable pageable);

    List<Electronic> findByBrandIgnoreCase(String brand);

    // Price-based queries using BigDecimal
    List<Electronic> findByPriceLessThan(BigDecimal maxPrice);

    List<Electronic> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Electronic> findByPriceGreaterThan(BigDecimal minPrice);

    // Category-based queries
    @Query("SELECT e FROM Electronic e WHERE LOWER(e.category) LIKE LOWER(CONCAT('%', :category, '%'))")
    List<Electronic> findByCategory(@Param("category") String category);

    @Query("SELECT e FROM Electronic e WHERE LOWER(e.category) LIKE LOWER(CONCAT('%', :category, '%'))")
    Page<Electronic> findByCategoryPaged(@Param("category") String category, Pageable pageable);

    List<Electronic> findByCategoryIgnoreCase(String category);

    // Stock availability queries
    List<Electronic> findByInStock(Boolean inStock);

    Page<Electronic> findByInStock(Boolean inStock, Pageable pageable);

    List<Electronic> findByInStockTrueAndPriceLessThan(BigDecimal maxPrice);

    // Find by brand and category
    List<Electronic> findByBrandIgnoreCaseAndCategoryIgnoreCase(String brand, String category);

    // Count queries
    long countByBrandIgnoreCase(String brand);

    long countByCategoryIgnoreCase(String category);

    long countByInStock(Boolean inStock);

    // Advanced search with multiple filters
    @Query("SELECT e FROM Electronic e WHERE " +
            "(:brand IS NULL OR LOWER(e.brand) = LOWER(:brand)) AND " +
            "(:category IS NULL OR LOWER(e.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
            "(:minPrice IS NULL OR e.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR e.price <= :maxPrice) AND " +
            "(:inStock IS NULL OR e.inStock = :inStock)")
    Page<Electronic> findWithFilters(@Param("brand") String brand,
                                     @Param("category") String category,
                                     @Param("minPrice") BigDecimal minPrice,
                                     @Param("maxPrice") BigDecimal maxPrice,
                                     @Param("inStock") Boolean inStock,
                                     Pageable pageable);

    @Query("SELECT DISTINCT e.brand FROM Electronic e WHERE e.brand IS NOT NULL ORDER BY e.brand")
    List<String> findAllDistinctBrands();

    @Query("SELECT DISTINCT e.category FROM Electronic e WHERE e.category IS NOT NULL ORDER BY e.category")
    List<String> findAllDistinctCategories();

    @Query("SELECT e FROM Electronic e WHERE e.price IS NOT NULL ORDER BY e.price DESC")
    List<Electronic> findTopByOrderByPriceDesc(Pageable pageable);

    @Query("SELECT e FROM Electronic e WHERE e.price IS NOT NULL ORDER BY e.price ASC")
    List<Electronic> findTopByOrderByPriceAsc(Pageable pageable);

    @Override
    Page<Electronic> findAll(Pageable pageable);

    Page<Electronic> findByBrand(String brand, Pageable pageable);
}
