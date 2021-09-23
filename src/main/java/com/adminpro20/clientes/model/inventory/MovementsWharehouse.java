package com.adminpro20.clientes.model.inventory;

import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.Supplier;
import com.adminpro20.clientes.model.manufacture.Production;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movements_wharehouse")
public class MovementsWharehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String code;
    @DateTimeFormat(pattern = "yyy/MM/dd")
    private GregorianCalendar fecha;
    public String description;
    public String batch;
    public BigDecimal unitCost;
    public BigDecimal totalCost;
    public BigDecimal entrance;
    public BigDecimal price;
    public BigDecimal subTotal;
    public BigDecimal issues;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="customer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Customer customer;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="supplier_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Supplier supplier;
}
