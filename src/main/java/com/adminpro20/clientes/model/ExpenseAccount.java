package com.adminpro20.clientes.model;


import com.adminpro20.clientes.model.bank.Bank;
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
@Table
public class ExpenseAccount extends BaseEntity{

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private GregorianCalendar date;
    private String checkNumber;
    private String typeOfCost;
    private BigDecimal total;
    private String notes;
    private boolean isPayment;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Bank bank;

}
