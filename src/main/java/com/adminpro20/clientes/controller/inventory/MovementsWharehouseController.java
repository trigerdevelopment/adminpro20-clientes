package com.adminpro20.clientes.controller.inventory;

import com.adminpro20.clientes.model.inventory.Inventory;
import com.adminpro20.clientes.repository.inventory.MovementsWharehouseRepository;
import com.adminpro20.clientes.service.inventory.MovementsWharehouseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("api/wharehouse")
public class MovementsWharehouseController {

    HttpHeaders headers = new HttpHeaders();

    public final MovementsWharehouseService wharehouseService;
    public final MovementsWharehouseRepository wharehouseDao;

    public MovementsWharehouseController(MovementsWharehouseService wharehouseService, MovementsWharehouseRepository wharehouseDao) {
        this.wharehouseService = wharehouseService;
        this.wharehouseDao = wharehouseDao;
    }

    @RequestMapping(value = "/get-inventory", method = RequestMethod.GET)
    public ResponseEntity<List<Inventory>> getAllBankTransaction() {

        List<Inventory> inventories =  wharehouseService.getInventory();


//        if (inventories.isEmpty()) {
//            headers.set("error", "no existen movimientos a la cuentas del Cliente");
//            return new ResponseEntity<List<Inventory>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
//        }
        return new ResponseEntity<List<Inventory>>(inventories, HttpStatus.OK);

    }

    @RequestMapping(value = "/get-raw-material-inventory", method = RequestMethod.GET)
    public ResponseEntity<List<?>> getRawMaterialInventory() {

        List<Inventory> inventories =  wharehouseService.getRawMaterialInventory();


//        if (inventories.isEmpty()) {
//            headers.set("error", "no existen movimientos a la cuentas del Cliente");
//            return new ResponseEntity<List<Inventory>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
//        }
        return new ResponseEntity<List<?>>(inventories, HttpStatus.OK);

    }
}
