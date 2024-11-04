package com.electronics.pgdata.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ElectronicDTO {
    private String id;
    private Price prices;
    private String asins;
    private String brand;
    private String[] categories;
    private LocalDateTime dateAdded;
    private LocalDateTime dateUpdated;
    private String ean;
    private String[] imageURLs;
    private String keys;
    private String manufacturer;
    private String manufacturerNumber;
    private String name;
    private String[] primaryCategories;
    private String[] sourceURLs;
    private String upc;
    private String weight;
    
    @Data
    public static class Price {
        private Double amountMax;
        private Double amountMin;
        private String availability;
        private String condition;
        private String currency;
        private LocalDateTime dateSeen;
        private Boolean isSale;
        private String merchant;
        private String shipping;
        private String sourceURLs;
    }
}
