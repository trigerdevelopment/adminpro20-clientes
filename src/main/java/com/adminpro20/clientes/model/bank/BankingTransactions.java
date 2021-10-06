package com.adminpro20.clientes.model.bank;

import com.adminpro20.clientes.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "banking_transactions")
public class BankingTransactions extends BaseEntity {

   @Column
   public Long cuenta;
   @Column
   public Date date;
   @Column
   public Date dateTransactions;
   @Column
   public Long reference;
   @Column
   public String description;
   @Column
   public Long codeTransaction;
   @Column
   public Long sucursal;
   @Column
   public BigDecimal deposit;
   @Column
   public BigDecimal bankWithdrawals;
   @Column
   public BigDecimal balance;
   @Column
   public Long movimiento;
   @Column
   public String details;
   @Column
   private boolean enabled = false;

}
