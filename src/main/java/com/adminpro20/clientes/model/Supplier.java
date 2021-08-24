package com.adminpro20.clientes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;
    @Column(name = "supplier_rfc")
    private String rfc;
    @Column(columnDefinition = "varchar(255) default 'Materias Primas'")
    private String categoria;
    @Column(columnDefinition = "varchar(255) default 'unica'")
    private String storeNum;
    private String creditDays;
//    private String address;
//    private String subAccount;
//    private BigDecimal initialBalance;
    private BigDecimal balance = BigDecimal.valueOf(0.00);
//    private BigDecimal balance;
//    private Boolean isTax;
    private Boolean status = false;
    private BigDecimal monthSales;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    CustomerAddress customerAddress;

}
