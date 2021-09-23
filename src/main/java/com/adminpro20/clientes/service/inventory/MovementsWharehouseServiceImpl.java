package com.adminpro20.clientes.service.inventory;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.InvoiceItems;
import com.adminpro20.clientes.model.inventory.Inventory;
import com.adminpro20.clientes.model.inventory.MovementsWharehouse;
import com.adminpro20.clientes.model.manufacture.Production;
import com.adminpro20.clientes.model.manufacture.RawMaterial;
import com.adminpro20.clientes.model.product.Product;
import com.adminpro20.clientes.repository.InvoiceRepository;
import com.adminpro20.clientes.repository.inventory.MovementsWharehouseRepository;
import com.adminpro20.clientes.repository.manufacture.ProductionRepository;
import com.adminpro20.clientes.repository.product.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class MovementsWharehouseServiceImpl implements MovementsWharehouseService{

    public final MovementsWharehouseRepository movementsWharehouseRepository;
    public final ProductRepository productRepository;
    public final ProductionRepository productionRepository;
    public final InvoiceRepository invoiceRepository;

    public MovementsWharehouseServiceImpl(MovementsWharehouseRepository movementsWharehouseRepository, ProductRepository productRepository, ProductionRepository productionRepository, InvoiceRepository invoiceRepository) {
        this.movementsWharehouseRepository = movementsWharehouseRepository;
        this.productRepository = productRepository;
        this.productionRepository = productionRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void sendProductionAlmacen(Production production) {



        MovementsWharehouse movementsWharehouse = new MovementsWharehouse();
        //    Optional<Production> getProduction = productionDao.findById(production.getId());

        movementsWharehouse.setBatch(production.getBatch());
        movementsWharehouse.setCode(production.getCode());
        movementsWharehouse.setDescription(production.getProduct());
        movementsWharehouse.setFecha(production.getInitialDate());
        movementsWharehouse.setEntrance(production.getQuantity());
        movementsWharehouse.setIssues(new BigDecimal("0.00"));
        movementsWharehouse.setUnitCost(production.getCost());
        movementsWharehouse.setTotalCost(production.getTotalCost());
//        movementsWharehouse.setProduction(production);

        movementsWharehouseRepository.save(movementsWharehouse);
        this.updateProductCost(production);

        List<RawMaterial> rawMaterialList = production.getRawMaterials();

    }

    @Override
    public void sendSupplierInvoiceAlmacen(Invoice invoice) {

//        List<InvoiceItems> invoiceItemsList = invoice.getInvoiceItems();
        Invoice invoice1 = invoiceRepository.findInvoiceSupplierById(invoice.getId(), invoice.getSupplier().getId());

        System.out.println("INVOICE ITEMS LENGHT " + invoice1.getInvoiceItems().size());

        for(InvoiceItems item: invoice1.getInvoiceItems()){

            MovementsWharehouse material = new MovementsWharehouse();

            material.setFecha(item.getDate());
            material.setEntrance(item.getCantidad());
            material.setIssues(new BigDecimal("0.00"));
            material.setUnitCost(item.getValorUnitario());
            material.setDescription(item.getDescripcion());
            material.setCode(item.getClaveProdServ());
            material.setTotalCost(item.getImporte());
//            material.setSupplier(invoice.getSupplier());
            material.setPrice(item.getValorUnitario());
            material.setSubTotal(item.getImporte());
            System.out.println("MOVEMENTS WHAREHOUSE " + material.getDescription());
            movementsWharehouseRepository.save(material);

        }

    }

    @Override
    public void sendCustomerInvoiceAlmacen(Invoice invoice) {

        List<InvoiceItems> invoiceItemsList = invoice.getInvoiceItems();

        for(InvoiceItems item: invoiceItemsList){
            MovementsWharehouse material = new MovementsWharehouse();
            Product product = productRepository.findProductByCode(item.getClaveUnidad());
            System.out.println("PRODUCT UNIT COST " + product.getUnitCost());
            System.out.println("ITEM CANTIDAD " + item.getCantidad());
            material.setFecha(item.getDate());
            material.setIssues(item.getCantidad());
            material.setEntrance(new BigDecimal("0.00"));

            GregorianCalendar calendar = invoice.getFecha();
            int date = calendar.get(Calendar.MONTH) +1;
            BigDecimal newCost =  productionRepository.getAveCostCodeByMonth(date, item.getClaveUnidad());
            if(newCost != null){
                System.out.println("NEW COST "+ newCost);
                material.setUnitCost(newCost);
                material.setTotalCost(newCost.multiply(item.getCantidad()));

            }
            material.setDescription(item.getDescripcion());
            material.setCode(item.getClaveUnidad());
            material.setUnitCost(product.getUnitCost());
            material.setTotalCost(product.getUnitCost().multiply(item.getCantidad()));
//            material.setCustomer(invoice.getCustomer());
            material.setPrice(item.getValorUnitario());
            material.setSubTotal(item.getImporte());
            movementsWharehouseRepository.save(material);
        }

    }

    @Override
    public List<MovementsWharehouse> sendCustomerInvoiceToAlmacen(Invoice invoice) {
        List<InvoiceItems> invoiceItemsList = invoice.getInvoiceItems();
        List<MovementsWharehouse> movementsWharehouses = new ArrayList<>();

        for(InvoiceItems item: invoiceItemsList){
            MovementsWharehouse material = new MovementsWharehouse();
            Product product = productRepository.findProductByCode(item.getClaveUnidad());
            material.setFecha(item.getDate());
            material.setIssues(item.getCantidad());
            material.setEntrance(new BigDecimal("0.00"));

            GregorianCalendar calendar = invoice.getFecha();
            int date = calendar.get(Calendar.MONTH) +1;
            BigDecimal newCost =  productionRepository.getAveCostCodeByMonth(date, item.getClaveUnidad());
            material.setUnitCost(product.getUnitCost());
            material.setTotalCost(product.getUnitCost().multiply(item.getCantidad()));
            if(newCost != null){
                material.setUnitCost(newCost);
                material.setTotalCost(newCost.multiply(item.getCantidad()));

            }
            material.setDescription(item.getDescripcion());
            material.setCode(item.getClaveUnidad());

//            material.setCustomer(invoice.getCustomer());
            material.setPrice(item.getValorUnitario());
            material.setSubTotal(item.getImporte());
            material.setCustomer(invoice.getCustomer());
            movementsWharehouses.add(material);
        }

        return movementsWharehouses;
    }

    @Override
    public List<MovementsWharehouse> sendSupplierInvoiceToAlmacen(Invoice invoice) {

//        List<InvoiceItems> invoiceItemsList = invoice.getInvoiceItems();
        List<MovementsWharehouse> movementsWharehouses = new ArrayList<>();

        System.out.println("INVOICE ITEMS LENGHT " + invoice.getInvoiceItems().size());

        for(InvoiceItems item: invoice.getInvoiceItems()){

            MovementsWharehouse material = new MovementsWharehouse();

            material.setFecha(item.getDate());
            material.setEntrance(item.getCantidad());
            material.setIssues(new BigDecimal("0.00"));
            material.setUnitCost(item.getValorUnitario());
            material.setCode(item.getClaveProdServ());
            material.setDescription(item.getDescripcion());

            if(item.getDescripcion().toLowerCase().contains("caja")){
                if(item.getDescripcion().toLowerCase().contains("triger")){
                    material.setDescription("CAJA GENERICA TRIGER");
                }
                if(item.getDescripcion().toLowerCase().contains("quitakilos")){
                    System.out.println("CAJA QUITAKILOS ");
                    material.setCode(item.getClaveProdServ().concat("1"));
                    material.setDescription("CAJA GENERICA QUITAKILOS");
                }

            }

            if(item.getDescripcion().toLowerCase().contains("etiqueta")){
                material.setDescription("ETIQUETA TORTILLA TRIGO");
                if(item.getDescripcion().toLowerCase().contains("snack")){
                    material.setCode(item.getClaveProdServ().concat("1"));
                    material.setDescription("ETIQUETA SNACK MIX");
                }
                if(item.getDescripcion().toLowerCase().contains("nopal")){
                    material.setCode(item.getClaveProdServ().concat("2"));
                    material.setDescription("ETIQUETA TORTILLA NOPAL");
                }

            }
            if(item.getDescripcion().toLowerCase().contains("bolsas")){
                if(item.getDescripcion().toLowerCase().contains("20")){
                    System.out.println("BOLSA 20X ");
                    material.setCode(item.getClaveProdServ().concat("1"));
                }

            }


//
            material.setTotalCost(item.getImporte());
//            material.setSupplier(invoice.getSupplier());
            material.setPrice(item.getValorUnitario());
            material.setSubTotal(item.getImporte());
            System.out.println("MOVEMENTS WHAREHOUSE " + material.getDescription());
            material.setSupplier(invoice.getSupplier());
            movementsWharehouseRepository.save(material);
            movementsWharehouses.add(material);
        }
        return movementsWharehouses;
    }

    @Override
    public List<MovementsWharehouse> sendRawMaterialToAlmacen(Production production) {

        List<MovementsWharehouse> movementsWharehouses = new ArrayList<>();
        List<RawMaterial> rawMaterialList = production.getRawMaterials();

        for(RawMaterial rawMaterial : rawMaterialList) {
            MovementsWharehouse movementsWharehouse = new MovementsWharehouse();
            movementsWharehouse.setBatch(production.getBatch());
            movementsWharehouse.setCode(rawMaterial.getCodeProduct());
            movementsWharehouse.setDescription(rawMaterial.getRawmaterial());
            movementsWharehouse.setUnitCost(rawMaterial.getUnitCost());
            movementsWharehouse.setTotalCost(rawMaterial.getTotal());
            movementsWharehouse.setEntrance(new BigDecimal("0.00"));
            movementsWharehouse.setIssues(rawMaterial.getQuantity());
            movementsWharehouse.setFecha(production.getInitialDate());
//            movementsWharehouseRepository.save(movementsWharehouse);
            movementsWharehouses.add(movementsWharehouse);


        }
        MovementsWharehouse movementsWharehouse = new MovementsWharehouse();
        movementsWharehouse.setBatch(production.getBatch());
        movementsWharehouse.setCode(production.getCode());
        movementsWharehouse.setDescription(production.getProduct());
        movementsWharehouse.setFecha(production.getInitialDate());
        movementsWharehouse.setEntrance(production.getQuantity());
        movementsWharehouse.setIssues(new BigDecimal("0.00"));
        movementsWharehouse.setUnitCost(production.getCost());
        movementsWharehouse.setTotalCost(production.getTotalCost());
        movementsWharehouses.add(movementsWharehouse);
        return movementsWharehouses;
    }

    @Override
    public List<Inventory> getInventory() {
        List<ExtractWharehouseService> wharehouseinventory = movementsWharehouseRepository.getInventory();
        List<Inventory> inventories = new ArrayList<>();
        for(ExtractWharehouseService p : wharehouseinventory) {
            Inventory inventory = new Inventory();
            inventory.setCode(p.getCode());
            inventory.setQuantity(p.getTotal());
            inventory.setUnitCost(p.getUnitCost());
            inventory.setProduct(p.getDescription());
            if(p.getTotal()>0){
                inventory.setTotalCost(new BigDecimal(p.getTotal()).multiply(p.getUnitCost()));
            }

            inventories.add(inventory);
        }


        return inventories;
    }

    @Override
    public List<Inventory> getRawMaterialInventory() {
        List<ExtractWharehouseService> wharehouseinventory = movementsWharehouseRepository.getRawMaterialInventory();
        List<Inventory> inventories = new ArrayList<>();
        for(ExtractWharehouseService p : wharehouseinventory) {
            Inventory inventory = new Inventory();
            inventory.setCode(p.getCode());
            inventory.setQuantity(p.getTotal());
            inventory.setUnitCost(p.getUnitCost());
            inventory.setProduct(p.getDescription());
            inventory.setTotalCost(new BigDecimal(p.getTotal()).multiply(p.getUnitCost()));
            inventories.add(inventory);
        }


        return inventories;
    }

    @Override
    public ResponseEntity<String> existInventory(int date, String code, BigDecimal quantity) {
        return null;
    }

    public void updateProductCost(Production production) {

        GregorianCalendar calendar = production.getInitialDate();
        int date = calendar.get(Calendar.MONTH) +1;
        BigDecimal newCost =  productionRepository.getAveCostCodeByMonth(date, production.getCode());
        Product product = productRepository.findProductByCode(production.getCode());
        product.setUnitCost(newCost);
        productRepository.save(product);

        switch (date){
            case 1:
                product.setJauCost(newCost);
                break;
            case 2:
                product.setFebCost(newCost);
                break;
            case 3:
                product.setMarCost(newCost);
                break;
            case 4:
                product.setAprCost(newCost);
                break;
            case 5:
                product.setMayCost(newCost);
                break;
            case 6:
                product.setJuneCost(newCost);
                break;
            case 7:
                product.setJulCost(newCost);
                break;
            case 8:
                product.setAugCost(newCost);
                break;
            case 9:
                product.setSepCost(newCost);
                break;
            case 10:
                product.setOctCost(newCost);
                break;
            case 11:
                product.setNovCost(newCost);
                break;
            case 12:
                product.setDecCost(newCost);
                break;

        }

    }

}
