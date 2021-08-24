package com.adminpro20.clientes.model.graphic;

import java.math.BigDecimal;

public interface SalesByProduct {

    String getClaveUnidad();
    String getDescripcion();
    Long getCantidad();
    BigDecimal getValorUnitario();
    BigDecimal getImporte();
}
