package com.adminpro20.clientes.repository.bank;

import com.adminpro20.clientes.model.bank.Bank;
import org.springframework.data.repository.CrudRepository;

public interface BankRepository extends CrudRepository<Bank, Long> {

    Boolean existsByBankAccount(String bankAccount);
    Bank findBankByBankAccount(String bankAccount);


}
