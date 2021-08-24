package com.adminpro20.clientes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;
    private String storeNum;
    private BigDecimal balance = BigDecimal.valueOf(0.00);
//    @Column(nullable = false,unique = true)
    private String rfc;
    @Column(columnDefinition = "varchar(255) default 'Cadenas'")
    private String categoria;
    private Boolean status = false;
    private BigDecimal monthSales;
    private String creditDays;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    CustomerAddress customerAddress;

//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "tipos_id")
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
 //   Tipos tipos;


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", storeNum='" + storeNum + '\'' +
                ", balance=" + balance +
                ", rfc='" + rfc + '\'' +
                ", status=" + status +
                ", monthSales=" + monthSales +
                '}';
    }
}
