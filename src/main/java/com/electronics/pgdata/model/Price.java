package com.electronics.pgdata.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Price {
    @Column(name = "amount_max")
    private Double amountMax;
    
    @Column(name = "amount_min")
    private Double amountMin;
    
    private String availability;
    private String condition;
    private String currency;
    
    @Column(name = "date_seen")
    private LocalDateTime dateSeen;
    
    @Column(name = "is_sale")
    private Boolean isSale;
    
    private String merchant;
    private String shipping;
    
    @Column(name = "source_urls")
    private String sourceURLs;
}
