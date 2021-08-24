package com.adminpro20.clientes.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Builder
@Getter
@Setter
public class InvoiceDto {

    private  Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Calendar fecha;
    private Calendar fechaPago;
    private String condicionesDePago;
    private String concept;
    private String nombreArchivo;
    private String company;
    private BigDecimal subTotal;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal total;
    private BigDecimal pago;
    private Long folio;
    private Boolean payment;



}
