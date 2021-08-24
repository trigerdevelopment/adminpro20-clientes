package com.adminpro20.clientes.repository.product;

import com.adminpro20.clientes.model.product.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDao extends CrudRepository<Category, Long> {

    // @Query("select c from Category c where c.catName = :category")
    Boolean existsByCatName(String category);


}