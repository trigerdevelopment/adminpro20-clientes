package com.adminpro20.clientes.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

@Getter
@Setter
@Entity
public class SupplierInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private GregorianCalendar fecha;
    private GregorianCalendar fechaPago;
    private String condicionesDePago;
    private String concept;
    private String nombreArchivo;
    private String company;
    private String sucursal;
    private BigDecimal subTotal;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal total;
    private BigDecimal pago;
    @Column(nullable = false, unique = true)
    private Long folio;
    private Boolean payment;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="customer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Supplier supplier;

}
