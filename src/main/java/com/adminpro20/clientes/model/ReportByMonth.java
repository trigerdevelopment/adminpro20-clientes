package com.adminpro20.clientes.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;

@Getter
@Setter
public class ReportByMonth {

    private String objName;
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
    HashMap<Integer, BigDecimal> report;
}
