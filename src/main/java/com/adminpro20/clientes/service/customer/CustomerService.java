package com.adminpro20.clientes.service.customer;

import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.service.CrudService;

public interface CustomerService extends CrudService<Customer, Long> {

    Boolean existsCustomerByRfc(String rfc);
    Customer findCustomerByCustomerRfc(String rfc);
    Customer findCustomerByCustomerRfcAndStoreNum(String rfc, String storeNum);
    Boolean existCustomerByCustomerRfcAndStoreNum(String rfc, String storeNum);
}
