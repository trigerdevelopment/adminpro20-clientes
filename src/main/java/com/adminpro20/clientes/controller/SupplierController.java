package com.adminpro20.clientes.controller;


import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.Supplier;
import com.adminpro20.clientes.repository.SupplierRepository;
import com.adminpro20.clientes.service.supplier.SupplierService;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("api/supplier")
public class SupplierController {

    HttpHeaders headers = new HttpHeaders();
    public final SupplierService supplierService;
    public final SupplierRepository supplierRepository;

    public SupplierController(SupplierService supplierService, SupplierRepository supplierRepository) {
        this.supplierService = supplierService;
        this.supplierRepository = supplierRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createInvoice(@RequestBody Supplier supplier) {

        Supplier supplierSaved = new Supplier();
        Map<String, Object> response = new HashMap<>();

        try {
            if( supplierService.existSupplierBySupplierRfcAndStoreNum(supplier.getRfc(),supplier.getStoreNum())) {
                throw new DataAccessException("El Proveedor ya existe") {
                };
            }

        }catch (DataAccessException e){
            response.put("message", "error en el servidor al grabar datos, intente de nuevo");
            response.put("error", Objects.requireNonNull(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("customer", supplierSaved);
        String message = "el Cliente se agrego a la base de datos";
        supplierSaved = supplierService.save(supplier);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }


    @RequestMapping(value = "/suppliers-by-query", method = RequestMethod.GET)
    public ResponseEntity<Page<Supplier>> getStudents(
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String sucursal,
            @RequestParam(required = false, defaultValue = "") String rfc,
            @RequestParam(required = false, defaultValue = "") String categoria,
            @RequestParam(required = false, defaultValue = "0") String total,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "100") Integer pageSize,
            @RequestParam(defaultValue = "company") String sortBy,
            @RequestParam(defaultValue = "true") boolean orderBy

    ) throws ParseException, DatatypeConfigurationException {
        Pageable pageable;

        if (orderBy) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
            System.out.println("DESC");
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
            System.out.println("ASC");
        }


        Page<Supplier> suppliers;

        suppliers = supplierRepository.findAllByCompanyContainingAndRfcContaining(company,rfc,pageable);
        System.out.println("Total de Elementos " + suppliers.getTotalElements());
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    // PETICION DE BUSQUEDA DE CUENTA DE BANCO BY ID
    @RequestMapping(value = "/get-supplier-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Supplier> getBankMovementRegisterById(@PathVariable(value = "id") Long id) {
        try {

            Supplier supplier = supplierRepository.findById(id).get();
            return new ResponseEntity<Supplier>(supplier, HttpStatus.OK);
        }catch (Error e){
            Supplier supplier  = null;
            return new ResponseEntity<Supplier>(supplier, HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSupplier(@RequestBody Supplier supplier) {

        Map<String, Object> response = new HashMap<>();
        Supplier supplier1 = null;
        try {
            supplier1 =  supplierService.save(supplier);

        }catch (DataAccessException e){
            response.put("message", "error en el servidor al grabar datos, intente de nuevo");
            response.put("error", Objects.requireNonNull(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "El Cliente se actualizo con exito");
        response.put("cliente",supplier);
        return new ResponseEntity <> (response, HttpStatus.ACCEPTED);
    }

}
