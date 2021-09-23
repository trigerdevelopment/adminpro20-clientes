package com.adminpro20.clientes.controller.bank;


import com.adminpro20.clientes.dto.Mensaje;
import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.Supplier;
import com.adminpro20.clientes.model.bank.Bank;
import com.adminpro20.clientes.model.bank.ExpenseType;
import com.adminpro20.clientes.model.product.Product;
import com.adminpro20.clientes.repository.bank.ExpenseTypeRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/api/expense-type")
public class ExpenseTypeController {

    public final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeController(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @RequestMapping(value = "/upload-service", method = RequestMethod.POST)
    public ResponseEntity<String> readProductCSVFile(@RequestParam("file") MultipartFile file) throws JAXBException {

        String message ="";
        // validate file
        if (file.isEmpty()) {
            message = "El Formato del Archivo: " + file.getOriginalFilename() + " esta erroneo o no contiene datos, favor de verificar" + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        } else {



            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                System.out.println("Vamos a convertir la file" + reader.toString());
                CsvToBeanBuilder csvToBeanBuilder = new CsvToBeanBuilder(reader);
                csvToBeanBuilder.withType(ExpenseType.class);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean csvToBean = csvToBeanBuilder
                        .build();
                List<ExpenseType> expenseTypes = csvToBean.parse();

                for(ExpenseType p : expenseTypes){

                    ExpenseType expenseType= new ExpenseType();
                    expenseTypeRepository.save(p);

                }

            } catch (Exception ex) {
                message ="Error en el archivo enviado";
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
            }
        }

        message = "You successfully uploaded " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createInvoice(@RequestBody ExpenseType expenseType) {


        try {

           expenseTypeRepository.save(expenseType);

        } catch (DataAccessException exception) {

            return new ResponseEntity<>("hubo un error al grabar datos", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);


    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getExpenseTypes() {

        List<ExpenseType> expenseTypes = (List<ExpenseType>) expenseTypeRepository.findAll();

        return new ResponseEntity<>(expenseTypes, HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createExpenseType(@RequestBody ExpenseType expenseType) {
        expenseTypeRepository.save(expenseType);
        return new ResponseEntity<>("se agrego a la base de datos con exito", HttpStatus.CREATED);

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateInvoice(@RequestBody ExpenseType expenseType) {


        expenseTypeRepository.save(expenseType);
        String message = "La Factura se agrego a la base de datos";
        return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/delete/", method = RequestMethod.GET)
    public ResponseEntity<?> deleteInvoiceById(@RequestParam Long id) {

        Map<String, Object> response = new HashMap<>();
        try {
            expenseTypeRepository.deleteById(id);

        } catch (DataAccessException exception) {

            response.put("message", "error en el servidor al grabar datos, intente de nuevo");
            response.put("error", Objects.requireNonNull(exception.getMessage()).concat(":").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "se borro con exito en la BD");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }
}
