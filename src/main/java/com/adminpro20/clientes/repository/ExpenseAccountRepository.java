package com.adminpro20.clientes.repository;

import com.adminpro20.clientes.model.ExpenseAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

public interface ExpenseAccountRepository extends PagingAndSortingRepository<ExpenseAccount, Long> {


    @Query("SELECT d FROM ExpenseAccount d WHERE d.date >=?1 AND d.date <=?2 AND d.checkNumber LIKE %?3% AND d.typeOfCost LIKE %?4% AND d.notes LIKE %?5% AND d.total >=?6 AND d.bank.id =?7")
    Page<ExpenseAccount> getAllExpenseAccountByQuery(GregorianCalendar ini, GregorianCalendar fin, String checkNumber, String typeOfCost, String notes, BigDecimal total1, Long id, Pageable pageable);

}
