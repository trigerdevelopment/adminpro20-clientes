package com.adminpro20.clientes.repository.bank;

import com.adminpro20.clientes.model.bank.BankingTransactions;
import org.springframework.data.repository.CrudRepository;

public interface BankingTransactionRepository extends CrudRepository<BankingTransactions,Long> {
}
