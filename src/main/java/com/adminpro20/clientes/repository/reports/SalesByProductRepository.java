package com.adminpro20.clientes.repository.reports;

import com.adminpro20.clientes.model.graphic.SalesByVolume;
import com.adminpro20.clientes.model.InvoiceItems;
import com.adminpro20.clientes.model.graphic.SalesByProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.GregorianCalendar;
import java.util.List;

public interface SalesByProductRepository extends CrudRepository<InvoiceItems, Long> {

    @Query("SELECT d.claveUnidad as claveUnidad, d.descripcion as descripcion, d.valorUnitario as valorUnitario, SUM(d.cantidad) as cantidad, sum(d.importe) as importe FROM InvoiceItems d WHERE d.date >= ?1 AND d.date < ?2 GROUP BY d.claveUnidad")
    List<SalesByProduct> getDataSalesByCustomer(GregorianCalendar date, GregorianCalendar date2);


    @Query("SELECT p FROM Invoice e JOIN e.invoiceItems p WHERE e.customer.id =?1")
    List<InvoiceItems> getInvoicesItem(Long id);

    @Query("SELECT p.claveUnidad as claveUnidad, p.descripcion as descripcion, SUM(p.cantidad) as cantidad FROM Invoice e JOIN e.invoiceItems p WHERE e.customer.id =?1 GROUP BY p.claveUnidad")
    List<SalesByProduct> getInvoicesItem2(Long id);

    @Query("SELECT p.descripcion as name, SUM(p.cantidad) as value FROM Invoice e JOIN e.invoiceItems p WHERE p.date >= ?1 AND p.date < ?2 AND e.customer.id =?3 GROUP BY p.claveUnidad")
    List<SalesByVolume> getSalesByVolume(GregorianCalendar date, GregorianCalendar date2, Long id);

}
