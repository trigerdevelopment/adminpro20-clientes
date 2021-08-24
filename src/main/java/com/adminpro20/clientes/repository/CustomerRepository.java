package com.adminpro20.clientes.repository;

import com.adminpro20.clientes.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    Boolean existsCustomerByRfc(String rfc);
    Customer findCustomerByRfc(String rfc);
    Customer findCustomerByRfcAndStoreNum(String rfc, String storeNum);
    Boolean existsCustomerByRfcAndStoreNum(String rfc, String storeNum);
    Page<Customer> findAllByCompanyContainingAndRfcContainingAndCategoriaContainingAndStoreNumContaining(String company,String rfc, String categoria,String storeNum, Pageable pageable);

}
