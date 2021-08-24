package com.adminpro20.clientes.controller;


import com.adminpro20.clientes.model.Tipos;
import com.adminpro20.clientes.model.product.Category;
import com.adminpro20.clientes.repository.product.CategoryDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    public final CategoryDao categoryDao;

    public ProductCategoryController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllInvoices() {
        try {
            List<Category> categories = (List<Category>) categoryDao.findAll();
            if (categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
