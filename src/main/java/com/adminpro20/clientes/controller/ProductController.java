package com.adminpro20.clientes.controller;


import com.adminpro20.clientes.model.product.Product;
import com.adminpro20.clientes.repository.product.ProductDao;
import com.adminpro20.clientes.repository.product.ProductRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Controller
@RequestMapping("api/product")
public class ProductController {

    public final ProductRepository productPagingRepository;

    HttpHeaders headers = new HttpHeaders();

    public final ProductDao productDao;

    public ProductController(ProductRepository productPagingRepository, ProductDao productDao) {
        this.productPagingRepository = productPagingRepository;
        this.productDao = productDao;
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
                csvToBeanBuilder.withType(Product.class);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean csvToBean = csvToBeanBuilder
                        .build();
                System.out.println("convert CSVBean" + csvToBean.toString());
                // convert `CsvToBean` object to list of users
                List<Product> products = csvToBean.parse();

                for(Product p : products){
                    System.out.println("Valores de la file " + p.toString());
                    Product product = new Product();
                    productDao.save(p);

                }
                // System.out.println(users);

                // TODO: save users in DB?

                // save users list on model
//                model.addAttribute("users", users);
//                model.addAttribute("status", true);

            } catch (Exception ex) {
//                model.addAttribute("message", "An error occurred while processing the CSV file.");
//                model.addAttribute("status", false);
            }
        }

        message = "You successfully uploaded " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


    @RequestMapping(value = "/add-product", method = RequestMethod.POST)
    public ResponseEntity<String> createBankAccount(@RequestBody Product product) {
        System.out.println("Creating Bank Account " + product.getProductName());

        if(productDao.existsProductByCode(product.getCode())){
            String message = "El codigo del Producto ya existe";
            return new ResponseEntity<String>(message, HttpStatus.CONFLICT);

        }else {
            Product upProduct = productDao.save(product);
            String message = "El Producto se agrego a la base de datos";
            return new ResponseEntity <String> (message,HttpStatus.OK);

        }

    }

    //  UPDATE PORDUCT
    @RequestMapping(value = "/update-product", method = RequestMethod.PUT)
    public ResponseEntity<String> updateSupplier(@RequestBody Product product) {
        System.out.println("Creating Bank Account " + product.getProductName());

        Product upProduct = productDao.save(product);
        String message = "El Producto se actualizo en la base de datos";
        return new ResponseEntity <String> (message,HttpStatus.OK);

    }


    @RequestMapping(value = "/get-products-by-query", method = RequestMethod.GET)
    public ResponseEntity<Page<Product>> getAllBankTransaction(
            @RequestParam(required = false,defaultValue = "") String code,
            @RequestParam(required = false,defaultValue = "") String productName,
            @RequestParam(required = false,defaultValue = "") String category,
            @RequestParam(required = false,defaultValue = "") String subCategory,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "25") Integer pageSize,
            @RequestParam(defaultValue = "code") String sortBy,
            @RequestParam(defaultValue = "true") boolean orderBy) {

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
//        Page<Product> products = productPagingRepository.findAll(paging);
        System.out.println("PAGE SIZE " + pageSize + ": " + pageNo + ": " + productName);
        Page<Product> products = productPagingRepository.findAllByCodeContainingAndProductNameContainingAndCategoryContainingAndSubCategoryContaining(code, productName,category,subCategory,pageable);

        headers.set("correcto", "se agrego la factura con exito");
        String message ="hello world";
        return new ResponseEntity<Page<Product>>(products, headers, HttpStatus.OK);
    }



    @RequestMapping(value = "/get-all-products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllBankTransaction() {

        List<Product> products = (List<Product>) productDao.findAll();
        if (products.isEmpty()) {
            headers.set("error", "no existen movimientos a la cuentas del Cliente");
            return new ResponseEntity<List<Product>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

    }

    // PETICION DE BUSQUEDA DE SUPPLIER BY ID
    @RequestMapping(value = "/get-product/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long id) {
        try {

            Product product =  productDao.findProductById(id);

            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }catch (Error e){
            Product product = null;
            return new ResponseEntity<Product>(product, HttpStatus.CONFLICT);
        }

    }



}
