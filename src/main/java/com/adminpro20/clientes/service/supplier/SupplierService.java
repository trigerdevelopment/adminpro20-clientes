package com.adminpro20.clientes.service.supplier;

import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.Supplier;
import com.adminpro20.clientes.service.CrudService;

public interface SupplierService extends CrudService<Supplier, Long> {

    Supplier findSupplierBySupplierRfcAndStoreNum(String rfc, String storeNum);
    Boolean existSupplierBySupplierRfcAndStoreNum(String rfc, String storeNum);
}
