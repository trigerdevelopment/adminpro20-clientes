package com.adminpro20.clientes.controller;


import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.ExpenseAccount;
import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.repository.ExpenseAccountRepository;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("api/expense")
public class ExpenseAccountController {

    HttpHeaders headers = new HttpHeaders();
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    GregorianCalendar gregorianCalendar;
    GregorianCalendar gregorianCalendar2;


    public final ExpenseAccountRepository expenseAccountRepository;

    public ExpenseAccountController(ExpenseAccountRepository expenseAccountRepository) {
        this.expenseAccountRepository = expenseAccountRepository;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createInvoice(@RequestBody ExpenseAccount expenseAccount) {


        Map<String, Object> response = new HashMap<>();
        try {
           ExpenseAccount expenseAccount1= expenseAccountRepository.save(expenseAccount);

        } catch (DataAccessException exception) {

            response.put("message", "Error al grabar en la Base de Datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(expenseAccount, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> updateInvoice(@RequestBody ExpenseAccount expenseAccount) {


        expenseAccountRepository.save(expenseAccount);
        String message = "La Factura se agrego a la base de datos";
        return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteInvoiceById(@PathVariable("id") long id) {

        try {
            expenseAccountRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("NO EXISTE EN LA BD");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/list-by-query", method = RequestMethod.GET)
    public ResponseEntity<?> getStudents(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false,defaultValue = "") String checkNumber,
            @RequestParam(required = false, defaultValue = "") String typeOfCost,
            @RequestParam(required = false, defaultValue = "") String notes,
            @RequestParam(required = false, defaultValue = "0") String total,
            @RequestParam(required = false, defaultValue = "") Long bankId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "100") Integer pageSize,
            @RequestParam(defaultValue = "fecha") String sortBy,
            @RequestParam(defaultValue = "true") boolean orderBy

    ) throws ParseException, DatatypeConfigurationException {
        Pageable pageable;

        if (orderBy) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        }

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        if (iniDate != null) {
            gregorianCalendar.setTime(iniDate);
        } else {
            gregorianCalendar = null;
        }
        gregorianCalendar2 = (GregorianCalendar) Calendar.getInstance();
        if (finalDate != null) {
            gregorianCalendar2.setTime(finalDate);
        } else {
            gregorianCalendar2 = null;
        }

        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 1000));
            System.out.println("INVOICE LIST pasamos por aca");
            Page<ExpenseAccount> expenseAccounts = expenseAccountRepository.getAllExpenseAccountByQuery(gregorianCalendar, gregorianCalendar2, checkNumber, typeOfCost, notes,
                    new BigDecimal(total), bankId, pageable);
            return new ResponseEntity<>(expenseAccounts, HttpStatus.OK);
        } catch (JDBCConnectionException exception ) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
        }


    }


}
