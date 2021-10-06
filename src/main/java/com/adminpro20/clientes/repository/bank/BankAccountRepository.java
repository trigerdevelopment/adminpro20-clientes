package com.adminpro20.clientes.repository.bank;

import com.adminpro20.clientes.model.bank.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
}
