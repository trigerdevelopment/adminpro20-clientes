package com.adminpro20.clientes.repository;

import com.adminpro20.clientes.model.CustomerCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiposRepository extends JpaRepository<CustomerCategories, Long> {
}
