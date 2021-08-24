package com.adminpro20.clientes.service.supplier;

import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.Supplier;
import com.adminpro20.clientes.repository.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SupplierServiceImp implements SupplierService{

    public final SupplierRepository supplierRepository;

    public SupplierServiceImp(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> findAll() {
        return (List<Supplier>) supplierRepository.findAll();
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Supplier> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Supplier save(Supplier object) {
        return supplierRepository.save(object);
    }

    @Override
    public void delete(Supplier object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<Supplier> object) {

    }

    @Override
    public Boolean exist(Supplier object) {
        return null;
    }

    @Override
    public Supplier findSupplierBySupplierRfcAndStoreNum(String rfc, String storeNum) {
        return null;
    }

    @Override
    public Boolean existSupplierBySupplierRfcAndStoreNum(String rfc, String storeNum) {
        return supplierRepository.existsSupplierByRfcAndStoreNum(rfc,storeNum);
    }
}
