package com.adminpro20.clientes.controller;

import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.CustomerAddress;
import com.adminpro20.clientes.model.Tipos;
import com.adminpro20.clientes.repository.CustomerRepository;
import com.adminpro20.clientes.repository.TiposRepository;
import com.adminpro20.clientes.service.customer.CustomerService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.text.ParseException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("api/customer")
public class CustomerController {

    HttpHeaders headers = new HttpHeaders();
    public final CustomerService customerService;
    public final CustomerRepository customerRepository;
    public final TiposRepository tiposRepository;

    public CustomerController(CustomerService customerService, CustomerRepository customerRepository, TiposRepository tiposRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.tiposRepository = tiposRepository;
    }

    @RequestMapping(value = "/get-all-customer", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getAllBankAccount() {

        List<Customer> customers = (List<Customer>) customerService.findAll();
        boolean resp = customers.isEmpty();
        System.out.println("RESP " + resp);
        if (resp) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Hello", "World!");
            headers.add("Web", "javadesde0.com");
//            headers.set("error", "no existen movimientos a la cuentas del Cliente");
            System.out.println("VACIO");
            return new ResponseEntity<List<Customer>>(headers, HttpStatus.SERVICE_UNAVAILABLE);//You many decide to return HttpStatus.NOT_FOUND
        }else {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createInvoice(@RequestBody Customer customer) {

        Customer customersaved = new Customer();
        Map<String, Object> response = new HashMap<>();

        try {
         if( customerService.existCustomerByCustomerRfcAndStoreNum(customer.getRfc(),customer.getStoreNum())) {
             throw new DataAccessException("El Cliente ya existe") {
             };
         }

        }catch (DataAccessException e){
            response.put("message", "error en el servidor al grabar datos, intente de nuevo");
            response.put("error", Objects.requireNonNull(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("customer", customersaved);
        String message = "el Cliente se agrego a la base de datos";
        customersaved = customerService.save(customer);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {

        Map<String, Object> response = new HashMap<>();
        Customer customer1 = null;
        try {
            customer1 =  customerService.save(customer);

        }catch (DataAccessException e){
            response.put("message", "error en el servidor al grabar datos, intente de nuevo");
            response.put("error", Objects.requireNonNull(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "El Cliente se actualizo con exito");
        response.put("cliente",customer1);
        return new ResponseEntity <> (response, HttpStatus.ACCEPTED);
    }


    @RequestMapping(value = "/customer-by-query", method = RequestMethod.GET)
    public ResponseEntity<Page<Customer>> getStudents(
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String sucursal,
            @RequestParam(required = false, defaultValue = "") String rfc,
            @RequestParam(required = false, defaultValue = "") String categoria,
            @RequestParam(required = false, defaultValue = "0") String total,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "100") Integer pageSize,
            @RequestParam(defaultValue = "company") String sortBy,
//            @RequestParam(defaultValue = "true") String orderBy
            @RequestParam(defaultValue = "true") boolean orderBy

    ) throws ParseException, DatatypeConfigurationException {
        Pageable pageable;
//        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
//        Integer year = calendar.get(Calendar.YEAR);
//        int date = calendar.get(Calendar.MONTH) +1;

        if (orderBy) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
            System.out.println("DESC");
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
            System.out.println("ASC");
        }


        Page<Customer> customers;
//        invoiceList = invoiceQueryService.getInvoices(
//                gregorianCalendar, gregorianCalendar2, iniFolio, finalFolio, company, compare, total, pageable);

//        Page<Invoice> page = new PageImpl<>(invoiceList);
        System.out.println("COMPANY ES " + company);
        System.out.println("SUCURSAL " + sucursal);
        System.out.println("RFC " + rfc);
        customers = customerRepository.findAllByCompanyContainingAndRfcContainingAndCategoriaContainingAndStoreNumContaining(company,rfc,categoria,sucursal,pageable);
        System.out.println("Total de Elementos " + customers.getTotalElements());
//        invoiceList = invoicePagingRepository.findInvoiceByYear2(gregorianCalendar,gregorianCalendar2, inFolio, finFolio, company, sucursal, new BigDecimal(total), new BigDecimal(total2), date, year, pageable);

//            invoiceList = invoicePagingRepository.findAllByFechaLessThanEqualAndFechaGreaterThanEqualAndFolioLessThanEqualAndFolioGreaterThanEqual(gregorianCalendar2,gregorianCalendar,
//                    finFolio, inFolio,pageable);

//        Page<Invoice> invoices = invoicePagingRepository.getSumTotalByMonth(gregorianCalendar,date, year, pageable);

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }


    // PETICION DE BUSQUEDA DE CUENTA DE BANCO BY ID
    @RequestMapping(value = "/get-customer-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getBankMovementRegisterById(@PathVariable(value = "id") Long id) {
        try {

            Customer customer = customerRepository.findById(id).get();
            return new ResponseEntity<Customer>(customer, HttpStatus.OK);
        }catch (Error e){
            Customer customer  = null;
            return new ResponseEntity<Customer>(customer, HttpStatus.CONFLICT);
        }

    }


}
