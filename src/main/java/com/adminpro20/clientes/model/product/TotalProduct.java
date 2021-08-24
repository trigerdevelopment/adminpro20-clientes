package com.adminpro20.clientes.model.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TotalProduct {

    public TotalProduct() {
    }

    public String claveUnidad;
    public String descripcion;
    public BigDecimal valorUnitario;
    public Long cantidad;
    public BigDecimal importe;

}
