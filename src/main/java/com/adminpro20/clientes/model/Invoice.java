package com.adminpro20.clientes.model;

import com.adminpro20.clientes.model.inventory.MovementsWharehouse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

@Getter
@Setter
@Entity
public class Invoice {

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
    private String folio;
    private Boolean payment;


    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="customer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Customer customer;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="supplier_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Supplier supplier;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItems> invoiceItems;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "invoice_payment", joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private List<Payment> payments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id")
    private List<MovementsWharehouse> movementsWharehouses;

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", fechaPago=" + fechaPago +
                ", condicionesDePago='" + condicionesDePago + '\'' +
                ", concept='" + concept + '\'' +
                ", nombreArchivo='" + nombreArchivo + '\'' +
                ", company='" + company + '\'' +
                ", sucursal='" + sucursal + '\'' +
                ", subTotal=" + subTotal +
                ", impuesto=" + impuesto +
                ", descuento=" + descuento +
                ", total=" + total +
                ", pago=" + pago +
                ", folio=" + folio +
                ", payment=" + payment +
                ", customer=" + customer +
                ", invoiceItems=" + invoiceItems +
                ", payments=" + payments +
                '}';
    }
}
