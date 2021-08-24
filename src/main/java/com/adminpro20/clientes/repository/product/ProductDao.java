package com.adminpro20.clientes.repository.product;

import com.adminpro20.clientes.model.product.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Long> {

    Product findProductByCode(String code);
    Product findProductById(Long id);
    Boolean  existsProductByCode(String code);



}