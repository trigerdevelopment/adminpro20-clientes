package com.adminpro20.clientes.service.cfdi;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.jaxb.Comprobante;

import javax.xml.bind.JAXBException;

public interface CFdiService {
    public <T> T contextFile(Class<T> tClass, String comprobante) throws JAXBException;
    public Invoice fillInvoice(Invoice invoice, Comprobante comprobante);
    public Integer convertToInteger(String str);
    public Boolean existBranch(Comprobante comprobante);
}
