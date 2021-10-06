package com.adminpro20.clientes.repository.reports;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.graphic.SalesByCurrency;
import com.adminpro20.clientes.model.report.GetExpenseTypeIf;
import com.adminpro20.clientes.model.report.SalesByMonthIf;
import com.adminpro20.clientes.service.CustomerSalesByMonthIf;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

public interface SalesReportsRepository extends PagingAndSortingRepository<Invoice, Long> {

    @Query("SELECT SUM(d.total) FROM Invoice d WHERE d.fecha >=?1 AND d.fecha <=?2 AND d.company LIKE %?3% AND d.sucursal LIKE %?4%")
    BigDecimal getSumTotalByDate(GregorianCalendar date1, GregorianCalendar date2, String company, String sucursal);

    @Query("SELECT SUM(d.total) FROM Invoice d WHERE Month(d.fecha)=?1 AND Year(d.fecha)=?2")
    BigDecimal  getSumTotalByMonth(int date, int year);

    @Query("SELECT SUM(d.total) FROM Invoice d WHERE Month(d.fecha)=?1 AND Year(d.fecha)=?2")
    List<SalesByMonthIf> getSalesByMonth(int date, int year);

//    @Query("SELECT SUM(d.total) FROM Invoice d GROUP BY Month(d.fecha)=1?")

   @Query("SELECT Month(d.fecha) as name, SUM(d.total) as value FROM Invoice d Where d.fecha >=?1 AND d.fecha <=?2 Group By Month(d.fecha)")
    List<SalesByCurrency> getSalesByMonthGroupByDates(GregorianCalendar date1, GregorianCalendar date2);

   @Query("SELECT Month(d.fecha) as name, SUM(d.total) as value FROM Invoice d Where Year(d.fecha)=?1 Group By Month(d.fecha)")
    List<SalesByCurrency> getSalesByMonthGroupByMonth(int year);

    @Query("SELECT d.company as company, d.sucursal as sucursal, SUM(d.total) as total FROM Invoice d WHERE Month(d.fecha)=1 AND Year(d.fecha)=2019  GROUP BY d.sucursal")
    List<CustomerSalesByMonthIf> getSalesByMonth();

    @Query("SELECT d.company as company, d.sucursal as sucursal, d.fecha as fecha, SUM(d.total) as total FROM Invoice d WHERE Month(d.fecha)=1 AND Year(d.fecha)=2019  GROUP BY DATE_FORMAT(d.fecha,'%YYYY-%mm-%dd')")
    List<CustomerSalesByMonthIf> getSalesByDay();

    @Query("SELECT SUM(d.total) FROM Invoice d WHERE Month(d.fecha)=?1 AND Year(d.fecha)=?2 AND d.customer.id = ?3")
    BigDecimal  getSumTotalByCustomer(int date, int year, Long id);


    @Query("SELECT SUM(d.total) FROM Invoice d WHERE d.fecha >= ?1 AND d.fecha <= ?2 AND d.company LIKE %?3% AND d.sucursal LIKE %?4% AND d.customer.id = ?5")
    BigDecimal  getSumTotalByCustomerByQuery(GregorianCalendar ini, GregorianCalendar fin, String company, String sucursal, Long id);

    @Query("SELECT SUM(d.total) FROM Invoice d WHERE d.fecha >=?1 AND d.fecha <=?2 AND d.customer.id =?3")
    BigDecimal  getSumTotalByCustomerByWeek(GregorianCalendar ini, GregorianCalendar fin, Long id);

//    @Query("SELECT SUM(d.total) FROM Invoice d WHERE d.fecha=?1 AND d.company LIKE %?3% AND d.sucursal LIKE %?4% AND d.customer.id =?5")
//    BigDecimal  getSumTotalByCustomerByDay(GregorianCalendar dateIni, String company, String sucursal, Long id);

//    @Query("SELECT SUM(d.total) FROM Invoice d WHERE WEEK_OF_YEAR(d.fecha) ='1' AND YEAR(d.fecha) =2019 AND d.company LIKE %?3% AND d.sucursal LIKE %?4% AND d.customer.id =?5")
//    BigDecimal  getSumTotalByCustomerByWeek2(GregorianCalendar dateIni, GregorianCalendar dateFin, String company, String sucursal, Long id);
//    @Query("SELECT d.company as company, d.sucursal as sucursal, FROM Invoice d WHERE Month(d.fecha)=1 AND Year(d.fecha)=2019 GROUP BY d.company")
//    List<CustomerSalesByMonthIf> getNotSalesByMonth();


//    @Query("SELECT SUM(d.total) FROM Invoice d WHERE Month(d.fecha)=?1 AND Year(d.fecha)=?2")
//    BigDecimal  getSumTotalByMonthAndYear(int date, int year);

}
