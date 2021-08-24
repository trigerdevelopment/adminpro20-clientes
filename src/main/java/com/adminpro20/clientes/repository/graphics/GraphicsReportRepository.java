package com.adminpro20.clientes.repository.graphics;

import com.adminpro20.clientes.model.graphic.SalesByCurrency;
import com.adminpro20.clientes.model.InvoiceItems;
import com.adminpro20.clientes.model.graphic.SalesByVolume;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.GregorianCalendar;
import java.util.List;

public interface GraphicsReportRepository extends CrudRepository<InvoiceItems, Long> {


  /*************************************************************************************************************************************
  *
  *                                        RETURN TOTAL SALES BY VOLUME OF EACH PIECES BETWEEN DATES
  **************************************************************************************************************************************/
    @Query("SELECT p.descripcion as name, SUM(p.cantidad) as value FROM Invoice e JOIN e.invoiceItems p WHERE p.date >= ?1 AND p.date < ?2 AND e.company LIKE %?3% AND e.sucursal LIKE %?4% GROUP BY p.claveUnidad")
    List<SalesByVolume> getSalesByVolumeBetweenDates(GregorianCalendar date, GregorianCalendar date2, String company, String sucursal);



    @Query("SELECT Month(d.fecha) as name, SUM(d.total) as value FROM Invoice d Where d.fecha >=?1 AND d.fecha <=?2 AND d.company LIKE %?3% AND d.sucursal LIKE %?4% Group By Month(d.fecha)")
    List<SalesByCurrency> getSalesByMonthGroupByDates(GregorianCalendar date1, GregorianCalendar date2, String company, String sucursal);
    /*************************************************************************************************************************************
     *
     *                      RETURN TOTAL SALES BY VOLUME OF EACH PIECES BETWEEN DATES AND BY CUSTOMER SELECTED
     **************************************************************************************************************************************/
    @Query("SELECT p.descripcion as name, SUM(p.cantidad) as value FROM Invoice e JOIN e.invoiceItems p WHERE p.date >= ?1 AND p.date < ?2 AND e.customer.id =?3 GROUP BY p.claveUnidad")
    List<SalesByVolume> getSalesByVolumeBetweenDatesAndCustomerId(GregorianCalendar date, GregorianCalendar date2, Long id);


   /***********************************************************************************************************************************
   *
   *                        Return sales by volume of pieces of each product by the year, group by month
   *
   ************************************************************************************************************************************/
    @Query("SELECT p.descripcion as name, SUM(p.cantidad) as value FROM Invoice e JOIN e.invoiceItems p WHERE Year(p.date)=?1 GROUP BY p.claveUnidad")
    List<SalesByVolume> getSalesByVolumeThisYear(Integer year);


}
