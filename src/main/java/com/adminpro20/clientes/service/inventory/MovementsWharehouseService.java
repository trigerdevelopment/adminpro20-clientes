package com.adminpro20.clientes.service.inventory;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.inventory.Inventory;
import com.adminpro20.clientes.model.inventory.MovementsWharehouse;
import com.adminpro20.clientes.model.manufacture.Production;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface MovementsWharehouseService {

     void sendProductionAlmacen(Production production);
     void sendSupplierInvoiceAlmacen(Invoice invoice);
     void sendCustomerInvoiceAlmacen(Invoice invoice);
     List<MovementsWharehouse> sendCustomerInvoiceToAlmacen(Invoice invoice);
     List<MovementsWharehouse> sendSupplierInvoiceToAlmacen(Invoice invoice);
     List<MovementsWharehouse> sendRawMaterialToAlmacen(Production production);
     List<Inventory> getInventory();
     List<Inventory> getRawMaterialInventory();
     ResponseEntity<String> existInventory(int date, String code, BigDecimal quantity);
}
