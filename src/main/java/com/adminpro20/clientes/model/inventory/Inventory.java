package com.adminpro20.clientes.model.inventory;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String product;
    private Long quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
}
