package com.adminpro20.clientes.service.invoice;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.InvoiceItems;
import com.adminpro20.clientes.service.CrudService;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public interface InvoiceService extends CrudService<Invoice, Long> {

    Boolean existsInvoiceByFolio(String folio);
    BigDecimal getSumTotalByDate(GregorianCalendar date1, GregorianCalendar date2);
    String getFolioFromComprobanteNode(String node,String compare, Document doc);
    List<InvoiceItems> setListConceptosAttributesAsString(String node, Document doc, GregorianCalendar date);

}
