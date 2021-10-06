package com.adminpro20.clientes.service.excel;

import com.adminpro20.clientes.model.bank.BankAccount;
import com.adminpro20.clientes.model.bank.BankingTransactions;
import com.adminpro20.clientes.repository.bank.BankAccountRepository;
import com.adminpro20.clientes.repository.bank.BankingTransactionRepository;
import com.adminpro20.clientes.util.ExcelHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    public final BankAccountRepository bankAccountRepository;
    public final BankingTransactionRepository bankingTransactionRepository;

    public ExcelService(BankAccountRepository bankAccountRepository, BankingTransactionRepository bankingTransactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankingTransactionRepository = bankingTransactionRepository;
    }

    public void save(MultipartFile file) {
        try {
            List<BankAccount> tutorials = ExcelHelper.excelToBankAccount(file.getInputStream());
            bankAccountRepository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public void saveBankingTrans(MultipartFile file) {
        try {
            List<BankingTransactions> tutorials = ExcelHelper.excelToBankMovements(file.getInputStream());
            bankingTransactionRepository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<BankAccount> getAllTutorials() {
        return (List<BankAccount>) bankAccountRepository.findAll();
    }
}
