package com.adminpro20.clientes.controller;


import com.adminpro20.clientes.model.Bank;
import com.adminpro20.clientes.repository.BankRepository;

import com.adminpro20.clientes.service.bank.BankService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("api/bank")
public class BankController {

    HttpHeaders headers = new HttpHeaders();
    public final BankService bankService;
    public final BankRepository bankRepository;

    public BankController(BankService bankService, BankRepository bankRepository) {
        this.bankService = bankService;
        this.bankRepository = bankRepository;
    }


    @RequestMapping(value = "/add-bank", method = RequestMethod.POST)
    public ResponseEntity<String> createBankAccount(@RequestBody Bank bank) {
        System.out.println("Creating Bank Account " + bank.getBankName());

        if(bankRepository.existsByBankAccount(bank.getBankAccount())){
            String message = "La Cuenta de Banco ya existe";
            return new ResponseEntity<String>(message, HttpStatus.CONFLICT);

        }else {
            bankService.save(bank);
            String message = "El Producto se agrego a la base de datos";
            return new ResponseEntity <String> (message,HttpStatus.OK);

        }

    }

    //  UPDATE PORDUCT
    @RequestMapping(value = "/update-bank", method = RequestMethod.PUT)
    public ResponseEntity<String> updateBankingAccount(@RequestBody Bank bank) {
       // System.out.println("Creating Bank Account " + product.getProductName());

        Bank updateBank = bankService.save(bank);
        String message = "El Producto se actualizo en la base de datos";
        return new ResponseEntity <String> (message,HttpStatus.OK);

    }




    @RequestMapping(value = "/get-all-bank-account", method = RequestMethod.GET)
    public ResponseEntity<List<Bank>> getAllBankAccount() {

        List<Bank> bank = (List<Bank>) bankService.findAll();
        boolean resp = bank.isEmpty();
        System.out.println("RESP " + resp);
        if (resp == true) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Hello", "World!");
            headers.add("Web", "javadesde0.com");
//            headers.set("error", "no existen movimientos a la cuentas del Cliente");
            System.out.println("VACIO");
            return new ResponseEntity<List<Bank>>(headers,HttpStatus.SERVICE_UNAVAILABLE);//You many decide to return HttpStatus.NOT_FOUND
        }else {
            return new ResponseEntity<List<Bank>>(bank, HttpStatus.OK);

        }

    }



    // PETICION DE BUSQUEDA DE CUENTA DE BANCO BY ID
    @RequestMapping(value = "/get-banking-account-byId/{id}", method = RequestMethod.GET)
    public ResponseEntity<Bank> getBankMovementRegisterById(@PathVariable(value = "id") Long id) {
        try {

            Bank bank =  bankService.findById(id).get();
            return new ResponseEntity<Bank>(bank, HttpStatus.OK);
        }catch (Error e){
           Bank bank  = null;
            return new ResponseEntity<Bank>(bank, HttpStatus.CONFLICT);
        }

    }


    // PETICION DE BUSQUEDA DE CUENTA DE BANCO BY ID
    @RequestMapping(value = "/get-banking-account-byAccount/{id}", method = RequestMethod.GET)
    public ResponseEntity<Bank> getBankByAccount(@PathVariable(value = "id") String bankAccount) {

        System.out.println("VALOR RECIVIDO " + bankAccount);
        try {

            Bank bank =  bankRepository.findBankByBankAccount(bankAccount);
            return new ResponseEntity<Bank>(bank, HttpStatus.OK);
        }catch (Error e){
            Bank bank  = null;
            return new ResponseEntity<Bank>(bank, HttpStatus.CONFLICT);
        }

    }


}
