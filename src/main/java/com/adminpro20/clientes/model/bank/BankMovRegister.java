package com.adminpro20.clientes.model.bank;

import com.adminpro20.clientes.model.BaseEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

@Getter
@Setter
@Entity
@Table(name = "bank_movement_register")
public class BankMovRegister extends BaseEntity {

   public String cuenta;

   public GregorianCalendar fechaOperacion;

   public GregorianCalendar fecha;

   public String referencia;

   public String descripcion;

   public String codTransac;

   public String sucursal;

   public BigDecimal depositos;

   public BigDecimal retiros;

   public BigDecimal saldo;

   public String movimiento;

   public String descripcionDetallada;

   private boolean enabled = false;

}
