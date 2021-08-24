package com.adminpro20.clientes.service;

import com.adminpro20.clientes.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {

    List<T> findAll();

    Page<Invoice> findAll(Pageable pageable);

    Optional<T> findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);

    void saveAll(List<T> object);

    Boolean exist(T object);
}