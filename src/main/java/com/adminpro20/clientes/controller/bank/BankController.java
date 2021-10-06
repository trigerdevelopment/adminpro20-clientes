package com.adminpro20.clientes.controller.bank;


import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.bank.Bank;
import com.adminpro20.clientes.model.bank.BankMovementCsv;
import com.adminpro20.clientes.repository.bank.BankMovementRepository;
import com.adminpro20.clientes.repository.bank.BankRepository;

import com.adminpro20.clientes.service.bank.BankMovService;
import com.adminpro20.clientes.service.bank.BankService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("api/bank")
public class BankController {

    HttpHeaders headers = new HttpHeaders();
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    GregorianCalendar gregorianCalendar;
    GregorianCalendar gregorianCalendar2;

    public final BankService bankService;
    public final BankMovService bankMovService;
    public final BankRepository bankRepository;
    public final BankMovementRepository bankMovementRepository;

    public BankController(BankService bankService, BankMovService bankMovService, BankRepository bankRepository, BankMovementRepository bankMovementRepository) {
        this.bankService = bankService;
        this.bankMovService = bankMovService;
        this.bankRepository = bankRepository;
        this.bankMovementRepository = bankMovementRepository;
    }


    @RequestMapping(value = "/add-bank", method = RequestMethod.POST)
    public ResponseEntity<String> createBankAccount(@RequestBody Bank bank) {
        System.out.println("Creating Bank Account " + bank.getBankName());

        if (bankRepository.existsByBankAccount(bank.getBankAccount())) {
            String message = "La Cuenta de Banco ya existe";
            return new ResponseEntity<String>(message, HttpStatus.CONFLICT);

        } else {
            bankService.save(bank);
            String message = "El Producto se agrego a la base de datos";
            return new ResponseEntity<String>(message, HttpStatus.OK);

        }

    }

    //  UPDATE PORDUCT
    @RequestMapping(value = "/update-bank", method = RequestMethod.PUT)
    public ResponseEntity<String> updateBankingAccount(@RequestBody Bank bank) {
        // System.out.println("Creating Bank Account " + product.getProductName());

        Bank updateBank = bankService.save(bank);
        String message = "El Producto se actualizo en la base de datos";
        return new ResponseEntity<String>(message, HttpStatus.OK);

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
            return new ResponseEntity<List<Bank>>(headers, HttpStatus.SERVICE_UNAVAILABLE);//You many decide to return HttpStatus.NOT_FOUND
        } else {
            return new ResponseEntity<List<Bank>>(bank, HttpStatus.OK);

        }

    }


    // PETICION DE BUSQUEDA DE CUENTA DE BANCO BY ID
    @RequestMapping(value = "/get-banking-account-byId/{id}", method = RequestMethod.GET)
    public ResponseEntity<Bank> getBankMovementRegisterById(@PathVariable(value = "id") Long id) {
        try {

            Bank bank = bankService.findById(id).get();
            return new ResponseEntity<Bank>(bank, HttpStatus.OK);
        } catch (Error e) {
            Bank bank = null;
            return new ResponseEntity<Bank>(bank, HttpStatus.CONFLICT);
        }

    }


    // PETICION DE BUSQUEDA DE CUENTA DE BANCO BY ID
    @RequestMapping(value = "/get-banking-account-byAccount/{id}", method = RequestMethod.GET)
    public ResponseEntity<Bank> getBankByAccount(@PathVariable(value = "id") String bankAccount) {

        System.out.println("VALOR RECIVIDO " + bankAccount);
        try {

            Bank bank = bankRepository.findBankByBankAccount(bankAccount);
            return new ResponseEntity<Bank>(bank, HttpStatus.OK);
        } catch (Error e) {
            Bank bank = null;
            return new ResponseEntity<Bank>(bank, HttpStatus.CONFLICT);
        }

    }

    @RequestMapping(value = "bank-movement/", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoiceById(@RequestParam Long id) {

        Optional<BankMovementCsv> bankMovementCsv;
        Map<String, Object> response = new HashMap<>();
        try {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if(bankMovementRepository.existsById(id)){
                bankMovementCsv = bankMovService.findById(id);
                response.put("message", "bank movements en la BD");
                response.put("invoice", bankMovementCsv);
                return new ResponseEntity<>(bankMovementCsv, HttpStatus.OK);
            }else {
                response.put("message", "factura no existe en la BD");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

        } catch (DataAccessException exception) {

            response.put("message", "la factura no existe en la Base de Datos");
            response.put("error", Objects.requireNonNull(exception.getMessage()).concat(":").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/upload-service", method = RequestMethod.POST)
    public ResponseEntity<String> readProductCSVFile(@RequestParam("file") MultipartFile file) throws JAXBException {

        String message = "";
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // validate file
        if (file.isEmpty()) {
            message = "El Formato del Archivo: " + file.getOriginalFilename() + " esta erroneo o no contiene datos, favor de verificar" + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        } else {


            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                System.out.println("Vamos a convertir la file" + reader.toString());
                CsvToBean<BankMovementCsv> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(BankMovementCsv.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<BankMovementCsv> bankregister = csvToBean.parse();


                for (BankMovementCsv p : bankregister) {
                    System.out.println("Valores de la file " + p.toString());
                    BankMovementCsv bankMovementCsv = new BankMovementCsv();
//                    BankMovRegister bankMovRegister = new BankMovRegister();
//                        bankMovRegister.setCuenta(p.cuenta);
                        bankMovementCsv.setCuenta(p.cuenta);
//                        bankMovRegister.setCodTransac(p.codTransac);
//                    bankMovementCsv.setCodTransac(p.codTransac);
                    bankMovementCsv.setDescripcion(p.descripcion);
//                        bankMovRegister.setDescripcion(p.descripcion);
//                        bankMovRegister.setMovimiento(p.movimiento);
                    bankMovementCsv.setMovimiento(p.movimiento);
//                        bankMovRegister.setDescripcionDetallada(p.descripcionDetallada);
                    bankMovementCsv.setDescripcionDetallada(p.descripcionDetallada);
//                        bankMovRegister.setDepositos(p.depositos);
                    bankMovementCsv.setDepositos(p.depositos);
//                        bankMovRegister.setRetiros(p.retiros);
                    bankMovementCsv.setRetiros(p.retiros);
//                        bankMovRegister.setReferencia(p.referencia);
                    bankMovementCsv.setReferencia(p.referencia);
//                        bankMovRegister.setSaldo(p.saldo);
                    bankMovementCsv.setSaldo(p.saldo);
                        Date date = df.parse(p.getFecha());
                        Date date2 = df.parse(p.getFechaOperacion());
                       GregorianCalendar cal = new GregorianCalendar();
                       cal.setTime(date);
//                       bankMovRegister.setFecha(cal);
                    bankMovementCsv.setFechag(cal);

                    GregorianCalendar cal2 = new GregorianCalendar();
                       cal2.setTime(date2);
//                       bankMovRegister.setFechaOperacion(cal);
                    bankMovementCsv.setFechaOpg(cal2);

//                    if (bankRepository.existsByBankAccount(p.getCuenta())) {
                        bankMovementRepository.save(bankMovementCsv);
//                    }
                }

            } catch (MultipartException multipartException){
                message = "Error en el Archivo: No es archivo Valido!";
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
            }

            catch (Exception ex) {
                message = "Error en el Archivo: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message);

            }
        }

        message = "You successfully uploaded " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @RequestMapping(value = "/get-all-movements", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBankTransaction(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date initialDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false,defaultValue = "") String cuenta,
            @RequestParam(required = false,defaultValue = "") String referencia,
            @RequestParam(required = false,defaultValue = "") String descripcion,
            @RequestParam(required = false,defaultValue = "") String movimiento,
            @RequestParam(required = false,defaultValue = "0.00") BigDecimal depositos,
            @RequestParam(required = false,defaultValue = "0.00") BigDecimal retiros,
            @RequestParam(required = false,defaultValue = "") String descripcionDetallada,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "25") Integer pageSize,
            @RequestParam(defaultValue = "fechag") String sortBy,
            @RequestParam(defaultValue = "true") boolean orderBy) {
        Pageable pageable;
        if (orderBy) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        }
        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        if (initialDate != null) {
            gregorianCalendar.setTime(initialDate);
        } else {
            gregorianCalendar = null;
        }

        gregorianCalendar2 = (GregorianCalendar) Calendar.getInstance();
        if (finalDate != null) {
            gregorianCalendar2.setTime(finalDate);
        } else {
            gregorianCalendar2 = null;
        }


        Page<BankMovementCsv> bankMovementCsvs = (Page<BankMovementCsv>) bankMovementRepository.getBankMovementByQuery(
               gregorianCalendar,gregorianCalendar2, cuenta,referencia,descripcion,movimiento,depositos,retiros,descripcionDetallada,pageable
        );

        return new ResponseEntity<>(bankMovementCsvs, HttpStatus.OK);

    }

    @RequestMapping(value = "/bank-movement/update", method = RequestMethod.POST)
    public ResponseEntity<String> updateInvoice(@RequestBody BankMovementCsv bankMovementCsv) {


        bankMovementRepository.save(bankMovementCsv);
        String message = "La Factura se agrego a la base de datos";
        return new ResponseEntity<String>(headers, HttpStatus.ACCEPTED);
    }



}
