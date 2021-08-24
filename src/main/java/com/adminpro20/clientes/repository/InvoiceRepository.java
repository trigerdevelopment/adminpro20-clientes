package com.adminpro20.clientes.repository;

import com.adminpro20.clientes.model.Invoice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {



    @Query("SELECT p FROM Invoice e JOIN e.invoiceItems p")
    List<Objects> getInvoices();


    @Query("SELECT SUM(d.total) FROM Invoice d WHERE d.fecha >=?1 AND d.fecha <=?2")
    BigDecimal  getSumTotalByDate(GregorianCalendar date1, GregorianCalendar date2);

    @Query("SELECT SUM(d.total) FROM Invoice d WHERE Month(d.fecha)=?1")
    BigDecimal  getSumTotalByMonth(int date);

    public List<Invoice> findAllByCustomerId(Long id);
//    public List<Invoice> findAllBySupplierId(Long id);

    public Invoice findInvoiceById(Long id);

//    @Query("SELECT e FROM Invoice e where e.supplier.id > 0")
//     Pageable<Invoice> findAllInvoicesPageableBySupplier(Pageable pageable);

//    @Query("SELECT e FROM Invoice e where e.supplier.id > 0")
//    public List<Invoice> findAllInvoicesBySupplier();
//    @Query("SELECT e FROM Invoice e GROUP BY e.supplier")
//    public List<Invoice> findAllInvoicesBySupplier();

    @Query("SELECT e FROM Invoice e where e.customer.id > 0")
    public List<Invoice> findAllInvoicesByCustomer();

    @Query("SELECT  SUM(e.total) FROM Invoice e where e.customer.id =?1")
    public BigDecimal getSumTotalByCustomer(Long id);

//    @Query("SELECT  COUNT(e.total) FROM Invoice e where e.supplier.id > 0")
//    public Integer getCountTotalBySupplier();

//    @Query("SELECT  SUM(e.total) FROM Invoice e where e.supplier.id =?1")
//    public BigDecimal getSumTotalBySupplier(Long id);

//    @Query("SELECT e FROM Invoice e where e.supplier.id =?1")
//    public Invoice getInvoiceSupplierById(Long id);

    @Query("SELECT e FROM Invoice e where e.folio =?1")
    public List<Invoice> findInvoiceByFolio(String id);

    public Invoice getInvoiceById(Long id);

    Boolean existsInvoiceByFolio(String folio);

    @Query("SELECT  e FROM Invoice e where e.customer.id =?1")
    public List<Invoice> findInvoiceWithNoPolizaByCustomer(Long id);

    @Query("SELECT e FROM Invoice e where e.id =?1 AND e.supplier.id =?2")
    public Invoice findInvoiceSupplierById(Long id, Long supplierId);

    //public List<Invoice> findAllByPolizas_Empty();

    // public List<Invoice> findAllByPolizas_EmptyAndCustomerId(Long id);


}
