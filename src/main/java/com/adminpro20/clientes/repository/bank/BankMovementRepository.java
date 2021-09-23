package com.adminpro20.clientes.repository.bank;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.bank.BankMovementCsv;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

public interface BankMovementRepository extends PagingAndSortingRepository<BankMovementCsv, Long> {

//    @Query("SELECT d FROM BankMovementCsv d WHERE d.fechaOperacion LIKE %?1% AND d.cuenta LIKE %?2%")
    @Query("SELECT d FROM BankMovementCsv d WHERE d.fechag >=?1 AND d.fechag <=?2 AND d.cuenta LIKE %?3% AND d.referencia LIKE %?4% AND d.descripcion LIKE %?5% AND d.movimiento LIKE %?6% AND d.depositos >=?7 AND d.retiros >=?8 AND d.descripcionDetallada LIKE %?9%")
    Page<BankMovementCsv> getBankMovementByQuery(GregorianCalendar ini, GregorianCalendar fin,String cuenta, String ref, String descripcion,String movimiento, BigDecimal depositos, BigDecimal retiros, String descDetal, Pageable pageable);

}
