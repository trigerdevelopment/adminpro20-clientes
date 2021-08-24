package com.adminpro20.clientes.service.cfdi;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.InvoiceItems;
import com.adminpro20.clientes.model.jaxb.Comprobante;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class CfdiServiceImp implements CFdiService{
    @Override
    public <T> T contextFile(Class<T> tClass, String comprobante) {

        try {
            StringReader com = new StringReader(comprobante);
            JAXBContext context = JAXBContext.newInstance(tClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Comprobante unmarshal = (Comprobante) unmarshaller.unmarshal(com);

            return (T) unmarshal;
        } catch (JAXBException e) {
            return null;
        }

    }

    public Invoice fillInvoice(Invoice invoice, Comprobante comprobante) {

        invoice.setFolio(comprobante.getFolio());
        invoice.setFecha(comprobante.getFecha().toGregorianCalendar());
        if (comprobante.getCondicionesDePago() != null) {
            Integer paymentDay = convertToInteger(comprobante.getCondicionesDePago());
            GregorianCalendar gregorianCalendar = comprobante.getFecha().toGregorianCalendar();
            gregorianCalendar.add(Calendar.DATE, paymentDay);
            invoice.setFechaPago(gregorianCalendar);
        }

        invoice.setTotal(comprobante.getTotal());
        invoice.setInvoiceItems(getConceptos(comprobante));
        return invoice;
    }

    @Override
    public Integer convertToInteger(String str) {
        String replace = str.replaceAll("\\s", "");
        String replaceTwo = replace.replaceAll("[^a-zA-Z]", "");
        String replaceTree = replace.replaceAll("[^0-9]", "");
        try {
            int valueIntger = Integer.valueOf(replaceTree);
            return valueIntger;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public Boolean existBranch(Comprobante comprobante) {
        try {
            if (comprobante.getAddenda().getFacturaInterfactura().getEncabezado().getNumSucursal() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Error error) {
            return false;
        }
    }


    public List<InvoiceItems> getConceptos(Comprobante comprobante) {

        List<Comprobante.Conceptos.Concepto> conceptos = comprobante.getConceptos().getConcepto();
        List<InvoiceItems> itemsList = new ArrayList<>();
        for (Comprobante.Conceptos.Concepto c : conceptos) {
            InvoiceItems invoiceItems = new InvoiceItems();
            invoiceItems.setCantidad(c.getCantidad());
            invoiceItems.setClaveProdServ(c.getClaveProdServ());
            invoiceItems.setDate(comprobante.getFecha().toGregorianCalendar());
            invoiceItems.setClaveUnidad(c.getNoIdentificacion());
            invoiceItems.setDescripcion(c.getDescripcion());
            invoiceItems.setImporte(c.getImporte());
            invoiceItems.setValorUnitario(c.getValorUnitario());
            invoiceItems.setUnidad(c.getUnidad());
            itemsList.add(invoiceItems);
        }

        return itemsList;
    }
}
