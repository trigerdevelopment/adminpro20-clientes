package com.adminpro20.clientes.controller;

import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.Supplier;
import com.adminpro20.clientes.repository.CustomerRepository;
import com.adminpro20.clientes.repository.InvoicePagingRepository;
import com.adminpro20.clientes.repository.InvoiceRepository;
import com.adminpro20.clientes.repository.SupplierRepository;
import com.adminpro20.clientes.service.invoice.InvoiceService;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/invoice")
public class CrudInvoiceController {
    HttpHeaders headers = new HttpHeaders();
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    GregorianCalendar gregorianCalendar;
    GregorianCalendar gregorianCalendar2;


    public final InvoiceService invoiceService;
    public final InvoiceRepository invoiceRepository;
    public final CustomerRepository customerRepository;
    public final SupplierRepository supplierRepository;
    public final InvoicePagingRepository invoicePagingRepository;


    public CrudInvoiceController(InvoiceService invoiceService, InvoiceRepository invoiceRepository, CustomerRepository customerRepository, SupplierRepository supplierRepository, InvoicePagingRepository invoicePagingRepository) {
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.supplierRepository = supplierRepository;
        this.invoicePagingRepository = invoicePagingRepository;
    }

//    @GetMapping()
//    public ResponseEntity<List<Invoice>> getAllInvoices() {
//        try {
////            invoices = invoiceService.findAll();
//            List<Invoice> invoices = new ArrayList<Invoice>(invoiceService.findAll());
//            if (invoices.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(invoices, HttpStatus.OK);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {

        Invoice invoiceNew;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Customer> customer = customerRepository.findById(invoice.getCustomer().getId());
            invoice.setCompany(customer.get().getCompany());
            invoice.setSucursal(customer.get().getStoreNum());
            invoiceNew = invoiceService.save(invoice);

        } catch (DataAccessException exception) {

            response.put("message", "la factura ya existe en la Base de Datos");
            response.put("error", Objects.requireNonNull(exception.getMessage()).concat(":").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "la factura se creo en la BD");
        response.put("cliente", invoiceNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);


    }

    @RequestMapping(value = "/supplier-add", method = RequestMethod.POST)
    public ResponseEntity<?> createSupplierInvoice(@RequestBody Invoice invoice) {

        Invoice invoiceNew;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Supplier> supplier = supplierRepository.findById(invoice.getSupplier().getId());
            invoice.setCompany(supplier.get().getCompany());
            invoice.setSucursal(supplier.get().getStoreNum());
            invoiceNew = invoiceService.save(invoice);

        } catch (DataAccessException exception) {

            response.put("message", "la factura ya existe en la Base de Datos");
            response.put("error", Objects.requireNonNull(exception.getMessage()).concat(":").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "la factura se creo en la BD");
        response.put("cliente", invoiceNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);


    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> updateInvoice(@RequestBody Invoice invoice) {

        System.out.println("INVOICE pago " + invoice.getPago());

        invoiceService.save(invoice);
        String message = "La Factura se agrego a la base de datos";
        return new ResponseEntity<String>(headers, HttpStatus.ACCEPTED);
    }


    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoiceById(@RequestParam Long id) {

        Optional<Invoice> invoice;
        Map<String, Object> response = new HashMap<>();
        try {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if(invoiceRepository.existsById(id)){
                invoice = invoiceService.findById(id);
                response.put("message", "factura en la BD");
                response.put("invoice", invoice);
                return new ResponseEntity<>(invoice, HttpStatus.OK);
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

    //    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//    @DeleteMapping("/delete/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteInvoiceById(@PathVariable("id") long id) {

        System.out.println("INVOICE ID " + id);
        try {
            invoiceService.deleteById(id);
            System.out.println("TODO SALIO CORRECTO");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("NO EXISTE EN LA BD");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @RequestMapping(value = "/invoice-by-query", method = RequestMethod.GET)
    public ResponseEntity<?> getStudents(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false,defaultValue = "") String iniFolio,
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String sucursal,
            @RequestParam(required = false, defaultValue = "0") String total,
            @RequestParam(required = false, defaultValue = "1000000") String total2,
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
            Page<Invoice> invoiceList = invoicePagingRepository.getAllCustomerInvoice(gregorianCalendar, gregorianCalendar2, iniFolio, company, sucursal,
                    new BigDecimal(total), new BigDecimal(total2), 0L, pageable);
            return new ResponseEntity<>(invoiceList, HttpStatus.OK);
        } catch (JDBCConnectionException exception ) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
        }


    }


    @RequestMapping(value = "/supplier-invoice-by-query", method = RequestMethod.GET)
    public ResponseEntity<Page<Invoice>> getSupplierInvoice(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false,defaultValue = "") String iniFolio,
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "0") String total,
            @RequestParam(required = false, defaultValue = "1000000") String total2,
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
        if(iniDate !=null){
            gregorianCalendar.setTime(iniDate);
        }else {
            gregorianCalendar = null;
        }
        gregorianCalendar2 = (GregorianCalendar) Calendar.getInstance();
        if (finalDate !=null){
            gregorianCalendar2.setTime(finalDate);
        }else {
            gregorianCalendar2 = null;
        }

        Page<Invoice> invoiceList;

    try {
        invoiceList = invoicePagingRepository.getAllSupplierInvoice(gregorianCalendar,gregorianCalendar2,
                iniFolio, company, new BigDecimal(total), new BigDecimal(total2), 0L, pageable);
        return new ResponseEntity<>(invoiceList, HttpStatus.OK);

    }catch (Error e){
        System.out.println("Error " + e);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    }



}


