package com.adminpro20.clientes.service.bank;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.bank.BankMovementCsv;
import com.adminpro20.clientes.repository.bank.BankMovementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankMovServiceImp implements BankMovService{

    public final BankMovementRepository bankMovementRepository;

    public BankMovServiceImp(BankMovementRepository bankMovementRepository) {
        this.bankMovementRepository = bankMovementRepository;
    }

    @Override
    public List<BankMovementCsv> findAll() {
        return null;
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<BankMovementCsv> findById(Long aLong) {
        return bankMovementRepository.findById(aLong);
    }

    @Override
    public BankMovementCsv save(BankMovementCsv object) {
        return null;
    }

    @Override
    public void delete(BankMovementCsv object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<BankMovementCsv> object) {

    }

    @Override
    public Boolean exist(BankMovementCsv object) {
        return null;
    }
}
