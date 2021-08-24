package com.adminpro20.clientes.model.manufacture;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "raw_material")
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeProduct;
    private BigDecimal quantity;
    private String rawmaterial;
    private BigDecimal unitCost;
    private BigDecimal total;

}
