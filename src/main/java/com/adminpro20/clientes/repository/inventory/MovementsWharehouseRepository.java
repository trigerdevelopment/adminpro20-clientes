package com.adminpro20.clientes.repository.inventory;

import com.adminpro20.clientes.model.inventory.MovementsWharehouse;
import com.adminpro20.clientes.service.inventory.ExtractWharehouseService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface MovementsWharehouseRepository extends CrudRepository<MovementsWharehouse, Long> {



    @Query("SELECT d.code as code, d.description as description, AVG(d.unitCost) as unitCost, SUM(d.entrance - d.issues) as total FROM MovementsWharehouse d WHERE Month(d.fecha)=1 GROUP BY d.code")
    List<ExtractWharehouseService> getWharehouseByMonth();

   @Query("SELECT d.code as code, d.description as description, AVG(d.unitCost) as unitCost, SUM(d.entrance - d.issues) as total FROM MovementsWharehouse d GROUP BY d.code")
    List<ExtractWharehouseService> getInventory();

   @Query("SELECT d.code as code, d.description as description, AVG(d.unitCost) as unitCost, SUM(d.entrance - d.issues) as total FROM MovementsWharehouse d WHERE d.entrance >0 GROUP BY d.code")
    List<ExtractWharehouseService> getRawMaterialInventory();

    @Query("SELECT SUM(d.entrance - d.issues) as total FROM MovementsWharehouse d WHERE Month(d.fecha)=?1 AND d.code = ?2")
    BigDecimal existInventary(int date, String code);

    @Query("SELECT SUM(d.totalCost) FROM MovementsWharehouse d WHERE Month(d.fecha)=?1 AND Year(d.fecha)=?2 AND d.customer.id >0")
    BigDecimal  getSalesCostByMonth(int date, int year);


}
