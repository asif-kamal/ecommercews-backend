package com.electronics.pgdata.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ProductPrice {
    private Float amountMax;
    private Float amountMin;
    private String availability;
    private String condition;
    private String currency;
    private String dateSeen;
    private Boolean isSale;
    private String merchant;
    private String shipping;
    private String sourceURLs;
}