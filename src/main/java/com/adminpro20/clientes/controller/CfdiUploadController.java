/*
*
* Este Controlador recibe una factura XML del Cliente desde el Front End, del tipo CFDI,
* todas las facturas del Cliente son enviadas a este Controlador Tiene connexion con Servicios
* para dar de alta la Factura, El Cliente, El Cliente con Sucursales y agrega los detalles de la Factura
*
*/


package com.adminpro20.clientes.controller;
import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.InvoiceItems;
import com.adminpro20.clientes.model.Supplier;
import com.adminpro20.clientes.model.inventory.MovementsWharehouse;
import com.adminpro20.clientes.model.jaxb.Comprobante;
import com.adminpro20.clientes.repository.SupplierRepository;
import com.adminpro20.clientes.service.cfdi.CFdiService;
import com.adminpro20.clientes.service.customer.CustomerService;
import com.adminpro20.clientes.service.inventory.MovementsWharehouseService;
import com.adminpro20.clientes.service.invoice.InvoiceService;
import com.adminpro20.clientes.service.reports.CustomerSalesReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/api/cfdi")
public class CfdiUploadController {

    public final CFdiService cfdiService;
    public final InvoiceService invoiceService;
    public final CustomerService customerService;
    public final CustomerSalesReportService customerSalesReportService;
    public final SupplierRepository supplierRepository;
    public final MovementsWharehouseService movementsWharehouseService;


    HttpHeaders headers;

    {
        headers = new HttpHeaders();
    }




    public CfdiUploadController(CFdiService cfdiService, InvoiceService invoiceService, CustomerService customerService, CustomerSalesReportService customerSalesReportService, SupplierRepository supplierRepository, MovementsWharehouseService movementsWharehouseService) {
        this.cfdiService = cfdiService;
        this.invoiceService = invoiceService;
        this.customerService = customerService;
        this.customerSalesReportService = customerSalesReportService;
        this.supplierRepository = supplierRepository;
        this.movementsWharehouseService = movementsWharehouseService;
    }


    @RequestMapping(value = "/customer-xml-file",  method = RequestMethod.POST)
    public ResponseEntity<String> customerXmlInvoice(@RequestBody String comprobante) throws JAXBException{
        String receptorRfc = "ANT021004RI7";
        Customer customer = new Customer();
        Invoice invoice = new Invoice();
        String message;
        try {

            Comprobante unmarshalComprobante = cfdiService.contextFile(Comprobante.class, comprobante);
            Invoice invoiceFilled = cfdiService.fillInvoice(invoice, unmarshalComprobante);
            customer.setCompany(unmarshalComprobante.getReceptor().getNombre());
            customer.setRfc(unmarshalComprobante.getReceptor().getRfc());
            customer.setCategoria("Cadenas");
            customer.setStatus(true);
            invoiceFilled.setCompany(customer.getCompany());
            invoiceFilled.setSucursal("");

            if (unmarshalComprobante.getAddenda() == null) {
                message = "El Comprobante no es una Factura Valida";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }


            if (unmarshalComprobante.getAddenda().getFacturaInterfactura() == null) {
                message = "Verifique que el documento sea una Factura Valida con Adenda";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }


            if (!unmarshalComprobante.getEmisor().getRfc().equals(receptorRfc)) {
                message = "Fail to save the folio " + unmarshalComprobante.getFolio() + "! exist";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
            if (invoiceService.existsInvoiceByFolio(unmarshalComprobante.getFolio())) {
                message = "Fail to save the folio " + unmarshalComprobante.getFolio() + "! exist";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }

   /*----------------------------- La Factura tiene sucursal?, existe el Cliente? si no existe lo da de Alta-----------------------------------*/

            if (cfdiService.existBranch(unmarshalComprobante)) {
                customer.setStoreNum(unmarshalComprobante.getAddenda().getFacturaInterfactura().getEncabezado().getNumSucursal());
                invoiceFilled.setSucursal(unmarshalComprobante.getAddenda().getFacturaInterfactura().getEncabezado().getNumSucursal());

   /*----------------------------- Cliente existe, se busca y se agrega a la Factura -----------------------------------*/

                if (customerService.findCustomerByCustomerRfcAndStoreNum(unmarshalComprobante.getReceptor().getRfc(), unmarshalComprobante.getAddenda().getFacturaInterfactura().getEncabezado().getNumSucursal()) != null){
                    Customer customerSaved = customerService.findCustomerByCustomerRfcAndStoreNum(unmarshalComprobante.getReceptor().getRfc(), unmarshalComprobante.getAddenda().getFacturaInterfactura().getEncabezado().getNumSucursal());
                    invoiceFilled.setCustomer(customerSaved);

   /*----------------------------- Cliente con sucursal noexiste, se agrega a la base de datos y se agrega a la factura-----------------------------------*/
                } else {
                    invoiceFilled.setCustomer(customerService.save(customer));
                }

              Invoice invoicewithsucursal =  invoiceService.save(invoiceFilled);
                movementsWharehouseService.sendCustomerInvoiceAlmacen(invoicewithsucursal);
                message = "La Factura con Folio: " + unmarshalComprobante.getFolio() + " " + "se agrego con exito";
                return ResponseEntity.status(HttpStatus.OK).body(message);

            }


   /*-----------------------------Cliente no tiene sucursal se pregunta si existe en la BD ----------------------------------------------------------*/

            if (!customerService.existsCustomerByRfc(unmarshalComprobante.getReceptor().getRfc())) {
                customer.setStoreNum("1");
                Customer customerSaved = customerService.save(customer);
                invoiceFilled.setCustomer(customerSaved);
            }else {
                Customer customerSaved = customerService.findCustomerByCustomerRfc(unmarshalComprobante.getReceptor().getRfc());
                invoice.setCustomer(customerSaved);
            }

          List<MovementsWharehouse> movementsWharehouses = movementsWharehouseService.sendCustomerInvoiceToAlmacen(invoice);
          invoice.setMovementsWharehouses(movementsWharehouses);
          invoiceService.save(invoiceFilled);
            message = "La Factura con Folio: " + unmarshalComprobante.getFolio() + " " + "se agrego con exito";
//            movementsWharehouseService.sendCustomerInvoiceAlmacen(invoiceSaved);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Error e) {
            System.out.println("Error " + e);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    /*
     *
     * Este Controlador recibe una factura XML del Proveedor desde el Front End, del tipo CFDI,
     * todas las facturas del Proveedor son enviadas a este Controlador Tiene connexion con Servicios
     * para dar de alta la Factura, El Proveedor, agrega los detalles de la Factura.
     * Este metido es diferente al del Cliente, el CFDI en algunos casos tienen problemas para darse de alta
     * asi que se escogió este método.
     *
     */


    @RequestMapping(value = "/supplier-xml-file", method = RequestMethod.POST)
    public ResponseEntity<String> supplierXmlInvoiceII(@RequestParam("file") MultipartFile file)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        File file1 = convert(file);
        String nombreArchivo = file.getOriginalFilename();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Invoice invoice = new Invoice();
        invoice.setNombreArchivo(nombreArchivo);
        String rfc;
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file1);
        System.out.println("DOC " + doc);
        doc.getDocumentElement().normalize();

        String folio = invoiceService.getFolioFromComprobanteNode("cfdi:Comprobante", "folio", doc);
        if (invoiceService.existsInvoiceByFolio(folio)) {
            String message = "La Factura con Folio: " + folio + "ya existe";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        rfc = invoiceService.getFolioFromComprobanteNode("cfdi:Emisor", "rfc", doc);
        if (!supplierRepository.existsSupplierByRfc(rfc)) {
            System.out.println("Supplier no existe");
            Supplier supplier = new Supplier();
            supplier.setRfc(rfc);
            supplier.setCompany(invoiceService.getFolioFromComprobanteNode("cfdi:Emisor", "nombre", doc ));
            supplierRepository.save(supplier);
            //xmlSupplierInvoiceService.saveSupplierByEmisorData(doc);
        } else {
            System.out.println("SUPPLIER SI EXISTE");
        }

        invoice.setSupplier(supplierRepository.findSupplierByRfc(rfc));
        // xmlSupplierInvoiceService.setToInvoiceComprobanteAttributes(doc, invoice);

        invoice.setFolio(invoiceService.getFolioFromComprobanteNode("cfdi:Comprobante", "folio", doc));
        invoice.setCompany(invoiceService.getFolioFromComprobanteNode("cfdi:Emisor", "nombre", doc));
        invoice.setSubTotal(new BigDecimal(invoiceService.getFolioFromComprobanteNode("cfdi:Comprobante", "subtotal", doc)));
        invoice.setTotal(new BigDecimal(invoiceService.getFolioFromComprobanteNode("cfdi:Comprobante", "total", doc)));
        if(invoiceService.getFolioFromComprobanteNode("cfdi:Comprobante", "descuento", doc) != null){
            invoice.setDescuento(new BigDecimal(invoiceService.getFolioFromComprobanteNode("cfdi:Comprobante", "descuento", doc)));
        }
        //  invoice.setDescuento(new BigDecimal(xmlSupplierInvoiceService.getFolioFromComprobanteNode("cfdi:Comprobante", "descuento", doc)));
        String timestampToParse = invoiceService.getFolioFromComprobanteNode("cfdi:Comprobante", "fecha", doc);
        Date fechaParse = sdf.parse(timestampToParse);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(fechaParse);
        invoice.setFecha(cal);
        invoice.setImpuesto(new BigDecimal(invoiceService.getFolioFromComprobanteNode("cfdi:Impuestos", "totalimpuestostrasladados", doc)));
//        InvoiceItems invoiceItems = new InvoiceItems();
        List<InvoiceItems> items = invoiceService.setListConceptosAttributesAsString("cfdi:Concepto", doc, cal);
        System.out.println("INVOICE ITEMS LENGHT " + items.size());
        invoice.setInvoiceItems(items);

//        invoice.setInvoiceItems(invoiceService.setListConceptosAttributesAsString("cfdi:Concepto", doc));
//        invoiceService.save(invoice);
        List<MovementsWharehouse> movementsWharehouses = movementsWharehouseService.sendSupplierInvoiceToAlmacen(invoice);
        invoice.setMovementsWharehouses(movementsWharehouses);
        Invoice invoice1 = invoiceService.save(invoice);
//        movementsWharehouseService.sendSupplierInvoiceAlmacen(invoice1);
        String message = "La Factura se agrego con exito";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    public File convert(MultipartFile file) throws IOException {
        final File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//        final boolean newFile = convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}

