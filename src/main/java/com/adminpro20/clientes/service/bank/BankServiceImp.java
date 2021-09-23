package com.adminpro20.clientes.service.bank;

import com.adminpro20.clientes.model.bank.Bank;
import com.adminpro20.clientes.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BankServiceImp implements BankService {
    @Override
    public List<Bank> findAll() {
        return null;
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Bank> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Bank save(Bank object) {
        return null;
    }

    @Override
    public void delete(Bank object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<Bank> object) {

    }

    @Override
    public Boolean exist(Bank object) {
        return null;
    }
}
