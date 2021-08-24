package com.adminpro20.clientes.service.customer;

import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService{

    public final CustomerRepository customerRepository;

    public CustomerServiceImp(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Customer> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Customer save(Customer object) {
        return customerRepository.save(object);
    }

    @Override
    public void delete(Customer object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<Customer> object) {

    }

    @Override
    public Boolean exist(Customer object) {
        return null;
    }


    @Override
    public Boolean existsCustomerByRfc(String rfc) {
        return customerRepository.existsCustomerByRfc(rfc);
    }

    @Override
    public Customer findCustomerByCustomerRfc(String rfc) {
        return customerRepository.findCustomerByRfc(rfc);
    }

    @Override
    public Customer findCustomerByCustomerRfcAndStoreNum(String rfc, String storeNum) {
        return customerRepository.findCustomerByRfcAndStoreNum(rfc, storeNum);
    }

    @Override
    public Boolean existCustomerByCustomerRfcAndStoreNum(String rfc, String storeNum) {
        return customerRepository.existsCustomerByRfcAndStoreNum(rfc,storeNum);
    }
}
