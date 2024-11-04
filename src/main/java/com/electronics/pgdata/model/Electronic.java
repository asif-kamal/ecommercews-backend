package com.electronics.pgdata.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cleaneddata02_electronics")
public class Electronic {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "asins")
    private String asins;

    @Column(name = "brand")
    private String brand;

    @Column(name = "categories")
    private String categories;

    @Column(name = "\"dateAdded\"")
    private String dateAdded;

    @Column(name = "\"dateUpdated\"")
    private String dateUpdated;

    @Column(name = "ean")
    private Float ean;

    @Column(name = "\"imageURLs\"")
    private String imageURLs;

    @Column(name = "keys")
    private String keys;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "\"manufacturerNumber\"")
    private String manufacturerNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "\"primaryCategories\"")
    private String primaryCategories;

    @Column(name = "\"sourceURLs\"")
    private String sourceURLs;

    @Column(name = "upc")
    private String upc;

    @Column(name = "weight")
    private String weight;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amountMax", column = @Column(name = "prices.amountMax")),
        @AttributeOverride(name = "amountMin", column = @Column(name = "prices.amountMin")),
        @AttributeOverride(name = "availability", column = @Column(name = "prices.availability")),
        @AttributeOverride(name = "condition", column = @Column(name = "prices.condition")),
        @AttributeOverride(name = "currency", column = @Column(name = "prices.currency")),
        @AttributeOverride(name = "dateSeen", column = @Column(name = "prices.dateSeen")),
        @AttributeOverride(name = "isSale", column = @Column(name = "prices.isSale")),
        @AttributeOverride(name = "merchant", column = @Column(name = "prices.merchant")),
        @AttributeOverride(name = "shipping", column = @Column(name = "prices.shipping")),
        @AttributeOverride(name = "sourceURLs", column = @Column(name = "prices.sourceURLs"))
    })
    private ProductPrice prices;
}