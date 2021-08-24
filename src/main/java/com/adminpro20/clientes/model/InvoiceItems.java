package com.adminpro20.clientes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

@Getter
@Setter
@Entity
@Table(name = "invoice_items")
public class InvoiceItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String claveProdServ;
    private BigDecimal cantidad;
    private GregorianCalendar date;
    private String unidad;
    private String claveUnidad;
    private String descripcion;
    private BigDecimal valorUnitario;
    private BigDecimal importe;
    private BigDecimal descuento;
//    @ManyToOne
//    public Invoice invoice;
}
