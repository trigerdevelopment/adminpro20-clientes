package com.adminpro20.clientes.controller.manufacture;


import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.inventory.MovementsWharehouse;
import com.adminpro20.clientes.model.manufacture.Production;
import com.adminpro20.clientes.repository.manufacture.ProductionRepository;
import com.adminpro20.clientes.service.inventory.MovementsWharehouseService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("api/manufacture")
public class ManufatureController {

    HttpHeaders headers = new HttpHeaders();
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    GregorianCalendar gregorianCalendar;
    GregorianCalendar gregorianCalendar2;


    public final ProductionRepository productionRepository;
    public final MovementsWharehouseService wharehouseService;

    public ManufatureController(ProductionRepository productionRepository, MovementsWharehouseService wharehouseService) {
        this.productionRepository = productionRepository;
        this.wharehouseService = wharehouseService;
    }


    @RequestMapping(value = "/add-production", method = RequestMethod.POST)
    public ResponseEntity<?> createProduction(@RequestBody Production production) {
        System.out.println("Creating Bank Account " + production.toString());

        try {

//            wharehouseService.sendProductionAlmacen(production);

           List<MovementsWharehouse> movementsWharehouseList = this.wharehouseService.sendRawMaterialToAlmacen(production);
           production.setMovementsWharehouses(movementsWharehouseList);
            productionRepository.save(production);

            String message = "La Producccion se agrego a la base de datos";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (DataAccessException e) {
//            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String message ="La produccion: ".concat(production.getBatch()).concat(" ya existe en la base de datos");
        return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteProdcutionById(@PathVariable("id") long id) {

        System.out.println("PRODUCTION ID " + id);
        try {
            productionRepository.deleteById(id);
            System.out.println("TODO SALIO CORRECTO");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("NO EXISTE EN LA BD");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/production-by-query", method = RequestMethod.GET)
    public ResponseEntity<?> getProduction(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date initialDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false,defaultValue = "") String batch,
            @RequestParam(required = false, defaultValue = "") String code,
            @RequestParam(required = false, defaultValue = "") String product,
            @RequestParam(required = false, defaultValue = "0") String quantity,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "100") Integer pageSize,
            @RequestParam(defaultValue = "initialDate") String sortBy,
            @RequestParam(defaultValue = "true") boolean orderBy

    ) throws ParseException, DatatypeConfigurationException {
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

        try {

            System.out.println("INVOICE LIST pasamos por aca");
            Page<Production> productions = productionRepository.getProductionByQuery(gregorianCalendar, gregorianCalendar2, batch, code, product,
                    new BigDecimal(quantity), pageable);
            String message = "todo ok";
            return new ResponseEntity<>(productions, HttpStatus.OK);
        } catch (JDBCConnectionException exception ) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
        }


    }



}
