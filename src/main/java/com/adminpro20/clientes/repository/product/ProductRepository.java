package com.adminpro20.clientes.repository.product;

import com.adminpro20.clientes.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findAllByCodeContainingAndProductNameContainingAndCategoryContainingAndSubCategoryContaining(String code, String productName,String category, String subCategory, Pageable pageable);
    Product findProductByCode(String code);
}
