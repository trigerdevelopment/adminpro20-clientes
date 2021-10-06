package com.adminpro20.clientes.model.product;


import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductCsv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByName
    public String code;

    @CsvBindByName
    public String productName;

    @CsvBindByName
    public BigDecimal unitPrice;

    @CsvBindByName
    public BigDecimal unitCost;

    @CsvBindByName
    public String category;

    @CsvBindByName
    public String subCategory;

    public BigDecimal jauCost;
    public BigDecimal febCost;
    public BigDecimal marCost;
    public BigDecimal aprCost;
    public BigDecimal mayCost;
    public BigDecimal juneCost;
    public BigDecimal julCost;
    public BigDecimal augCost;
    public BigDecimal sepCost;
    public BigDecimal octCost;
    public BigDecimal novCost;
    public BigDecimal decCost;

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", unitCost=" + unitCost +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                '}';
    }
}
