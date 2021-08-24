package com.adminpro20.clientes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String claveProdServ;
    private BigDecimal cantidad;
    private GregorianCalendar date;
    private String descripcion;

}
