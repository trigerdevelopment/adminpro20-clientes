package com.adminpro20.clientes.service.invoice;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.InvoiceItems;
import com.adminpro20.clientes.repository.InvoiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    public final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> findAll() {
        return (List<Invoice>) invoiceRepository.findAll();
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    @Override
    public Optional<Invoice> findById(Long aLong) {
        return invoiceRepository.findById(aLong);
    }

    @Override
    public Invoice save(Invoice object) {
        return invoiceRepository.save(object);
    }

    @Override
    public void delete(Invoice object) {
        invoiceRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        invoiceRepository.deleteById(aLong);
    }

    @Override
    public void saveAll(List<Invoice> object) {

    }

    @Override
    public Boolean exist(Invoice object) {
        return null;
    }

    @Override
    public Boolean existsInvoiceByFolio(String folio) {
        return invoiceRepository.existsInvoiceByFolio(folio);
    }

    @Override
    public BigDecimal getSumTotalByDate(GregorianCalendar date1, GregorianCalendar date2) {
        return invoiceRepository.getSumTotalByDate(date1, date2);
    }

    @Override
    public String getFolioFromComprobanteNode(String node, String compare, Document doc) {
        String folio = "";
        NodeList nodeList = doc.getElementsByTagName(node);
        StringBuilder sb = new StringBuilder("\n");

        for (int i = 0; i < nodeList.getLength(); i++) {

            NamedNodeMap attributes = nodeList.item(i).getAttributes();
            for (int j = 0; j < attributes.getLength(); j++) {
                sb.append("\t--> ")
                        .append(attributes.item(j).getNodeName())
                        .append(": ")
                        .append(attributes.item(j).getNodeValue())
                        .append("\n");
//                System.out.println("ATTRIBUTES NAME: " + attributes.item(j).getNodeName());
                if (attributes.item(j).getNodeName().toLowerCase().equals(compare)) {
                    System.out.println(
                            "EL " + compare.toUpperCase() + " ES:-> " + attributes.item(j).getNodeValue());
                    return attributes.item(j).getNodeValue();
                }
            }
        }

        return null;
    }

    @Override
    public List<InvoiceItems> setListConceptosAttributesAsString(String node, Document doc, GregorianCalendar date) {
        List<InvoiceItems> invoiceItemsList = new ArrayList<>();
        NodeList nodeList = doc.getElementsByTagName("cfdi:Concepto");

        StringBuilder sb = new StringBuilder("\n");
        for (int i = 0; i < nodeList.getLength(); i++) {

            NamedNodeMap attributes = nodeList.item(i).getAttributes();
            InvoiceItems invoiceItems = new InvoiceItems();

            for (int j = 0; j < attributes.getLength(); j++) {

                sb.append("\t- ")
                        .append(attributes.item(j).getNodeName())
                        .append(": ")
                        .append(attributes.item(j).getNodeValue())
                        .append("\n");
                if (attributes.item(j).getNodeName().toLowerCase().equals("cantidad")) {
                    System.out.println("Si es Cantidad es " + attributes.item(j).getNodeValue());
                    invoiceItems.setCantidad(new BigDecimal(attributes.item(j).getNodeValue()));
                    invoiceItems.setDate(date);
                }
                       if (attributes.item(j).getNodeName().toLowerCase().equals("claveprodserv")) {
                           System.out.println("Si es ClaveProdServ es " + attributes.item(j).getNodeValue());
                           invoiceItems.setClaveProdServ(attributes.item(j).getNodeValue());
                       }
                         if (attributes.item(j).getNodeName().toLowerCase().equals("claveunidad")) {
                         System.out.println("Si es ClaveUnidad es " + attributes.item(j).getNodeValue());
                         invoiceItems.setClaveUnidad(attributes.item(j).getNodeValue());
                       }

                    if (attributes.item(j).getNodeName().toLowerCase().equals("descripcion")) {
                        System.out.println("Si es Descripcion es " + attributes.item(j).getNodeValue());
                        invoiceItems.setDescripcion(attributes.item(j).getNodeValue());
                    }
                    if (attributes.item(j).getNodeName().toLowerCase().equals("importe")) {
                        System.out.println("Si es Importe es " + attributes.item(j).getNodeValue());
                        invoiceItems.setImporte(new BigDecimal(attributes.item(j).getNodeValue()));
                    }
                    if (attributes.item(j).getNodeName().toLowerCase().equals("tasaocuota")) {
                        System.out.println("Si es TasaOCuota es " + attributes.item(j).getNodeValue());
                        invoiceItems.setImporte(new BigDecimal(attributes.item(j).getNodeValue()));
                    }
                    if (attributes.item(j).getNodeName().toLowerCase().equals("unidad")) {
                        System.out.println("Si es Unidad es " + attributes.item(j).getNodeValue());
                        invoiceItems.setUnidad(attributes.item(j).getNodeValue());
                    }
                    if (attributes.item(j).getNodeName().toLowerCase().equals("valorunitario")) {
                        System.out.println("Si es ValorUnitario es " + attributes.item(j).getNodeValue());
                        invoiceItems.setValorUnitario(new BigDecimal(attributes.item(j).getNodeValue()));
                    }


                    System.out.println("VALOR DE J: " + j);



            }
            invoiceItemsList.add(invoiceItems);
        }

        return invoiceItemsList;

    }
}
