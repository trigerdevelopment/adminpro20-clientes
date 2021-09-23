package com.adminpro20.clientes.model.bank;

import com.adminpro20.clientes.model.conta.SubCuenta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
//@Builder(builderClassName = "CustomerBuild")
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankName;
    private String bankAccount;
    private String subAccount;
    private BigDecimal initialBalance;
    private BigDecimal balance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcuenta_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    SubCuenta subCuenta;


}