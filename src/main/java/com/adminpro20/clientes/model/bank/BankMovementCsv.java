package com.adminpro20.clientes.model.bank;

import com.adminpro20.clientes.model.BaseEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

@Getter
@Setter
@Entity
@Table(name = "bank_movement_csv")
public class BankMovementCsv extends BaseEntity {

    @CsvBindByName
    @Column
    public String cuenta;

    @CsvBindByName
    @Transient
    public String fechaOperacion;
    @Column
    public GregorianCalendar fechaOpg;

    @CsvBindByName
    @Transient
    public String fecha;
    @Column
    public GregorianCalendar fechag;

    @CsvBindByName
    @Column
    public String referencia;

    @CsvBindByName
    @Column
    public String descripcion;

//    @CsvBindByName
//    @Column
//    public String codTransac;

//    @CsvBindByName
//    @Column
//    public String sucursal;

    @CsvBindByName
    @Column
    public BigDecimal depositos;

    @CsvBindByName
    @Column
    public BigDecimal retiros;

    @CsvBindByName
    @Column
    public BigDecimal saldo;

    @CsvBindByName
    @Column
    public String movimiento;

    @CsvBindByName
    @Column
    public String descripcionDetallada;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = false;

    @Column
    private String typeOfExpense;

    @Column
    private String supplierName;

    @Column
    private String customerName;


}
