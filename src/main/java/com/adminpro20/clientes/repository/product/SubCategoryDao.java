package com.adminpro20.clientes.repository.product;

import com.adminpro20.clientes.model.product.SubCategory;
import org.springframework.data.repository.CrudRepository;

public interface SubCategoryDao extends CrudRepository<SubCategory, Long> {

    Boolean existsBySubCatName(String subcategory);

}