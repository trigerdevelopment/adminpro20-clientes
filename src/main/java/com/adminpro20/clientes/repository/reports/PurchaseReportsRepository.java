package com.adminpro20.clientes.repository.reports;

import com.adminpro20.clientes.model.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;

public interface PurchaseReportsRepository extends PagingAndSortingRepository<Supplier,Long> {

    @Query("SELECT SUM(d.total) FROM Invoice d WHERE Month(d.fecha)=?1 AND Year(d.fecha)=?2 AND d.supplier.id = ?3")
    BigDecimal getPurchaseBySupplierByMonth(int date, int year, Long id);
}
