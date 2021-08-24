package com.adminpro20.clientes.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

@Getter
@Setter
@Entity
public class CustomerSalesByMonth{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String sucursal;
    private String fecha;
    private int date;
    private BigDecimal sales;
    private BigDecimal january;
    private BigDecimal february;
    private BigDecimal march;
    private BigDecimal april;
    private BigDecimal may;
    private BigDecimal june;
    private BigDecimal july;
    private BigDecimal august;
    private BigDecimal september;
    private BigDecimal october;
    private BigDecimal november;
    private BigDecimal december;
    HashMap<Integer, BigDecimal> salesReport;

}
