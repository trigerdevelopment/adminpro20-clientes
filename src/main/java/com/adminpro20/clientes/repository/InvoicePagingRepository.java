package com.adminpro20.clientes.repository;

import com.adminpro20.clientes.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

public interface InvoicePagingRepository extends PagingAndSortingRepository<Invoice, Long> {

//     Page<Invoice> findAllByFechaLessThanEqualAndFechaGreaterThanEqualAndFolioLessThanEqualAndFolioGreaterThanEqual(GregorianCalendar date1, GregorianCalendar date2, Long folioFin, Long folioIni,Pageable pageable);
//     Page<Invoice> findAllByFechaLessThanEqualAndFechaGreaterThanEqualAndFolioLessThanEqualAndFolioGreaterThanEqualAndCompanyContainingAndSucursalContainingAndTotalGreaterThanEqualAndTotalLessThanEqual(GregorianCalendar date1, GregorianCalendar date2, Long folioFin, Long folioIni, String company, String sucursal, BigDecimal total, BigDecimal total2,Pageable pageable);

          @Query("SELECT d FROM Invoice d WHERE d.fecha >=?1 AND d.fecha <=?2 AND d.folio LIKE %?3% AND d.company LIKE %?4% AND d.total >=?5 AND d.total <=?6 AND d.supplier.id >=?7")
          Page<Invoice> getAllSupplierInvoice(GregorianCalendar ini, GregorianCalendar fin, String folio,String company,BigDecimal total1, BigDecimal total2, Long id, Pageable pageable);

          @Query("SELECT d FROM Invoice d WHERE d.fecha >=?1 AND d.fecha <=?2 AND d.folio LIKE %?3% AND d.company LIKE %?4% AND d.customer.storeNum LIKE %?5% AND d.total >=?6 AND d.total <=?7 AND d.customer.id >=?8")
          Page<Invoice> getAllCustomerInvoice(GregorianCalendar ini, GregorianCalendar fin, String folio, String company, String sucursal, BigDecimal total1, BigDecimal total2, Long id, Pageable pageable);

     /*-------------------------------Get all Invoices by Customer Id ---------------------------------*/
//     Page<Invoice> findAllByFechaLessThanEqualAndFechaGreaterThanEqualAndCustomerId(GregorianCalendar date1, GregorianCalendar date2,Long id,Pageable pageable);
//     Page<Invoice> findAllByFechaLessThanEqualAndFechaGreaterThanEqualAndCustomerId(GregorianCalendar date1, GregorianCalendar date2,Long id,Pageable pageable);
     Page<Invoice> findAllByFechaGreaterThanEqualAndFechaLessThanEqualAndCustomerId(GregorianCalendar date1, GregorianCalendar date2,Long id,Pageable pageable);
     Page<Invoice> findAllByFechaGreaterThanEqualAndFechaLessThanEqual(GregorianCalendar date1, GregorianCalendar date2,Pageable pageable);

//     @Query("SELECT d FROM Invoice d WHERE d.fecha >=?1 AND d.fecha <=?2 AND d.folio >=?3 AND d.folio <=?4 AND d.company like 5  AND d.sucursal like 6 AND d.total >=?7 AND d.total <=?8 AND Year(d.fecha)=?9");
//     Page<Invoice>  findInvoiceByYear(GregorianCalendar date1, GregorianCalendar date2, Long folio1, Long folio2, String company, String sucursal, BigDecimal total1, BigDecimal total2, int year, Pageable pageable);
//     @Query("SELECT d FROM Invoice d WHERE d.fecha >=?1 AND d.fecha <=?2 ");
//     Page<Invoice> findInvoiceByYear2(GregorianCalendar date,GregorianCalendar date2,Long folio1, Long folio2, String company,String sucursal, BigDecimal ttl1, BigDecimal ttl2,int month, int year, Pageable pageable);

     @Query("SELECT d FROM Invoice d WHERE Month(d.fecha)=?1 AND Year(d.fecha)=?2")
     Page<Invoice>  findInvoiceByThisYer(Integer date, Integer year, Pageable pageable);

     @Query("SELECT d FROM Invoice d WHERE Month(d.fecha)=?1 AND Year(d.fecha)=?2")
     List<Invoice> findInvoiceByThisYear(Integer date, Integer year);

     @Query("SELECT d FROM Invoice d WHERE d.fecha >=?1 AND d.fecha <=?2")
     List<Invoice> findInvoiceByDate(GregorianCalendar dateInitial, GregorianCalendar dateFinal);

     @Query("SELECT d FROM Invoice d WHERE Year(d.fecha)=?1")
     List<Invoice> findInvoiceThisYear(Integer year);

     @Query("SELECT d FROM Invoice d WHERE Year(d.fecha)=?1 AND d.customer.id=?2")
     List<Invoice> findInvoiceThisYearByCustomerId(Integer year, Long id);
}

