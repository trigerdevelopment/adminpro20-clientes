package com.adminpro20.clientes.repository.manufacture;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.manufacture.Production;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

public interface ProductionRepository  extends PagingAndSortingRepository<Production, Long> {

    Boolean existsProductionByBatch(String code);
    @Query("SELECT AVG(d.cost) FROM Production d WHERE Month(d.initialDate)=?1 AND d.code = ?2")
    BigDecimal getAveCostCodeByMonth(int date, String code);

    @Query("SELECT d FROM Production d WHERE d.initialDate >=?1 AND d.initialDate <=?2 AND d.batch LIKE %?3% AND d.code LIKE %?4% AND d.product LIKE %?5% AND d.quantity >=?6")
    Page<Production> getProductionByQuery(GregorianCalendar ini, GregorianCalendar fin, String batch, String code, String product, BigDecimal quantity,Pageable pageable);


}
