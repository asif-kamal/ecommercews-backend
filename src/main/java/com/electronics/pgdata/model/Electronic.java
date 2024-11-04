package com.electronics.pgdata.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "electronics")
public class Electronic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amountMax", column = @Column(name = "prices_amount_max")),
        @AttributeOverride(name = "amountMin", column = @Column(name = "prices_amount_min")),
        @AttributeOverride(name = "availability", column = @Column(name = "prices_availability")),
        @AttributeOverride(name = "condition", column = @Column(name = "prices_condition")),
        @AttributeOverride(name = "currency", column = @Column(name = "prices_currency")),
        @AttributeOverride(name = "dateSeen", column = @Column(name = "prices_date_seen")),
        @AttributeOverride(name = "isSale", column = @Column(name = "prices_is_sale")),
        @AttributeOverride(name = "merchant", column = @Column(name = "prices_merchant")),
        @AttributeOverride(name = "shipping", column = @Column(name = "prices_shipping")),
        @AttributeOverride(name = "sourceURLs", column = @Column(name = "prices_source_urls"))
    })
    private Price prices;
    
    private String asins;
    private String brand;
    
    @Column(columnDefinition = "text[]")
    private String[] categories;
    
    @Column(name = "date_added")
    private LocalDateTime dateAdded;
    
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;
    
    private String ean;
    
    @Column(name = "image_urls", columnDefinition = "text[]")
    private String[] imageURLs;
    
    private String keys;
    private String manufacturer;
    
    @Column(name = "manufacturer_number")
    private String manufacturerNumber;
    
    private String name;
    
    @Column(name = "primary_categories", columnDefinition = "text[]")
    private String[] primaryCategories;
    
    @Column(name = "source_urls", columnDefinition = "text[]")
    private String[] sourceURLs;
    
    private String upc;
    private String weight;
}