package com.adminpro20.clientes.model.manufacture;

import com.adminpro20.clientes.model.inventory.MovementsWharehouse;
import com.adminpro20.clientes.model.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "production")
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String product;
    private GregorianCalendar initialDate;
    private GregorianCalendar finalDate;
    @Column(nullable = false, unique = true)
    private String batch;
    private String observation;
    private BigDecimal cost;
    private BigDecimal totalCost;
    private BigDecimal quantity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "production_id")
    private List<RawMaterial> rawMaterials;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="product_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Product productName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "production_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<MovementsWharehouse> movementsWharehouses;
}
