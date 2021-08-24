package com.adminpro20.clientes.repository;

import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SupplierRepository extends PagingAndSortingRepository<Supplier, Long> {

    Boolean existsSupplierByRfc(String rfc);
    Supplier findSupplierByRfc(String rfc);
    Supplier findSupplierByRfcAndStoreNum(String rfc, String storeNum);
    Boolean existsSupplierByRfcAndStoreNum(String rfc, String storeNum);
    Page<Supplier> findAllByCompanyContainingAndRfcContaining(String company, String rfc, Pageable pageable);

}
