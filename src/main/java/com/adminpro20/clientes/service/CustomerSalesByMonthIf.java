package com.adminpro20.clientes.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

public interface CustomerSalesByMonthIf {

    String getCompany();
    String getSucursal();
    BigDecimal getTotal();
    GregorianCalendar getFecha();
}
