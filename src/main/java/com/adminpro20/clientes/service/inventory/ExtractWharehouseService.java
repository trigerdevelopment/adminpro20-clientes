package com.adminpro20.clientes.service.inventory;

import java.math.BigDecimal;

public interface ExtractWharehouseService {

    String getCode();
    String getDescription();
    BigDecimal getUnitCost();
    Long getTotal();
    BigDecimal getEntrance();
    BigDecimal getIssues();
}
