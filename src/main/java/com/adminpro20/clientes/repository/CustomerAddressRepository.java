package com.adminpro20.clientes.repository;

import com.adminpro20.clientes.model.CustomerAddress;
import org.springframework.data.repository.CrudRepository;

public interface CustomerAddressRepository extends CrudRepository<CustomerAddress, Long> {
}
