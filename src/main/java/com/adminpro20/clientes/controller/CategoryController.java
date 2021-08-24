package com.adminpro20.clientes.controller;


import com.adminpro20.clientes.model.Tipos;
import com.adminpro20.clientes.repository.TiposRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/api/categories")
public class CategoryController {

    HttpHeaders headers = new HttpHeaders();
    public final TiposRepository tiposRepository;

    public CategoryController(TiposRepository tiposRepository) {
        this.tiposRepository = tiposRepository;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllInvoices() {
        try {
            List<Tipos> customerTypeList = tiposRepository.findAll();
            if (customerTypeList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customerTypeList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createInvoice(@RequestBody Tipos tipos) {

        Tipos customerType1;
        Map<String, Object> response = new HashMap<>();
        try {
            customerType1 = tiposRepository.save(tipos);

        } catch (DataAccessException exception) {

            response.put("message", "error en el servidor al grabar datos, intente de nuevo");
            response.put("error", Objects.requireNonNull(exception.getMessage()).concat(":").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "se creo en la BD");
        response.put("customerType", customerType1);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }
 @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<?> deleteType(@RequestParam Long id) {
     System.out.println("ID = " + id);
        Map<String, Object> response = new HashMap<>();
        try {
            tiposRepository.deleteById(id);

        } catch (DataAccessException exception) {

            response.put("message", "error en el servidor al grabar datos, intente de nuevo");
            response.put("error", Objects.requireNonNull(exception.getMessage()).concat(":").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "se borro con exito en la BD");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

}
