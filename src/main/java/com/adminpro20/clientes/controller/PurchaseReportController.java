package com.adminpro20.clientes.controller;


import com.adminpro20.clientes.model.CustomerSalesByMonth;
import com.adminpro20.clientes.model.ReportByMonth;
import com.adminpro20.clientes.model.Supplier;
import com.adminpro20.clientes.repository.SupplierRepository;
import com.adminpro20.clientes.repository.reports.PurchaseReportsRepository;
import com.adminpro20.clientes.service.CustomerSalesByMonthIf;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("api/purchase")
public class PurchaseReportController {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    GregorianCalendar gregorianCalendar;

    public final SupplierRepository supplierRepository;
    public final PurchaseReportsRepository purchaseReportsRepository;

    public PurchaseReportController(SupplierRepository supplierRepository, PurchaseReportsRepository purchaseReportsRepository) {
        this.supplierRepository = supplierRepository;
        this.purchaseReportsRepository = purchaseReportsRepository;
    }


    @RequestMapping("/get-purchase-by-month/")
    public ResponseEntity<List<ReportByMonth>> getPurchaseByMonth(
            @RequestParam(required = false ) @DateTimeFormat(pattern = DATE_PATTERN) Date date
    ) {
        System.out.println("LLEGADA DE DATE " + date);
        int year =0;
        if (date != null) {
            gregorianCalendar.setTime(date);
            year = gregorianCalendar.get(Calendar.YEAR);
            System.out.println("AÑO A CONSIDERAR " + year);

        } else {
            gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
            year =  Calendar.getInstance().get(Calendar.YEAR);
            System.out.println("AÑO ACTUAL " + year);
        }
//        gregorianCalendar.setTime(date);

        List<ReportByMonth> reportByMonths= new ArrayList<>();
        List<Supplier> suppliers = new ArrayList<>();
        suppliers = (List<Supplier>) supplierRepository.findAll();

        for(Supplier c: suppliers){

            CustomerSalesByMonth customerSalesByMonth = new CustomerSalesByMonth();
            ReportByMonth reportByMonth = new ReportByMonth();
            for(int i = 1; i<13 ; i++){

                if(purchaseReportsRepository.getPurchaseBySupplierByMonth(i,year,c.getId()) !=null){
                    BigDecimal total = purchaseReportsRepository.getPurchaseBySupplierByMonth(i,year, c.getId());
                    switch (i){
                        case 1:
                            reportByMonth.setJanuary(total);
                            break;
                        case 2:
                            reportByMonth.setFebruary(total);
                            break;
                        case 3:
                            reportByMonth.setMarch(total);
                            break;
                        case 4:
                            reportByMonth.setApril(total);
                            break;
                        case 5:
                            reportByMonth.setMay(total);
                            break;
                        case 6:
                            reportByMonth.setJune(total);
                            break;
                        case 7:
                            reportByMonth.setJuly(total);
                            break;
                        case 8:
                            reportByMonth.setAugust(total);
                            break;
                        case 9:
                            reportByMonth.setSeptember(total);
                            break;
                        case 10:
                            reportByMonth.setOctober(total);
                            break;
                        case 11:
                            reportByMonth.setNovember(total);
                            break;
                        case 12:
                            reportByMonth.setDecember(total);
                            break;
                    }
//                    customerSalesByMonth.setId(c.getId());
                    reportByMonth.setObjName(c.getCompany());
                    reportByMonth.setSucursal(c.getStoreNum());
//                customerSalesByMonthList.add(customerSalesByMonth);

                }else {

                    switch (i) {
                        case 1:
                            reportByMonth.setJanuary(BigDecimal.valueOf(0.00));
                            break;
                        case 2:
                            reportByMonth.setFebruary(BigDecimal.valueOf(0.00));
                            break;
                        case 3:
                            reportByMonth.setMarch(BigDecimal.valueOf(0.00));
                            break;
                        case 4:
                            reportByMonth.setApril(BigDecimal.valueOf(0.00));
                            break;
                        case 5:
                            reportByMonth.setMay(BigDecimal.valueOf(0.00));
                            break;
                        case 6:
                            reportByMonth.setJune(BigDecimal.valueOf(0.00));
                            break;
                        case 7:
                            reportByMonth.setJuly(BigDecimal.valueOf(0.00));
                            break;
                        case 8:
                            reportByMonth.setAugust(BigDecimal.valueOf(0.00));
                            break;
                        case 9:
                            reportByMonth.setSeptember(BigDecimal.valueOf(0.00));
                            break;
                        case 10:
                            reportByMonth.setOctober(BigDecimal.valueOf(0.00));
                            break;
                        case 11:
                            reportByMonth.setNovember(BigDecimal.valueOf(0.00));
                            break;
                        case 12:
                            reportByMonth.setDecember(BigDecimal.valueOf(0.00));
                            break;
                    }

//                    customerSalesByMonth.setId(c.getId());
                    reportByMonth.setObjName(c.getCompany());
                    reportByMonth.setSucursal(c.getStoreNum());
                    c.setBalance(BigDecimal.valueOf(0.00));
                }

//                System.out.println("Customer " + c.toString());
            }
            reportByMonths.add(reportByMonth);
        }

//        List<CustomerSalesByMonthIf> customerSalesByMonths = salesReportsRepository.getSalesByMonth();
//        List<CustomerSalesByMonthIf> customerNoSales = salesReportsRepository.getNotSalesByMonth();
//        List<CustomerSalesByMonth> customerSalesByMonthArrayList = new ArrayList<>();
//        for(CustomerSalesByMonthIf p : customerSalesByMonths) {
//            CustomerSalesByMonth customerSalesByMonth = new CustomerSalesByMonth();

//            customerSalesByMonth.setSales(p.getTotal());
//            customerSalesByMonth.setCustomerName(p.getCompany());
//            customerSalesByMonth.setSucursal(p.getSucursal());
//             customerSalesByMonthArrayList.add(customerSalesByMonth);
//            inventory.setQuantity(p.getTotal());
//            inventory.setUnitCost(p.getUnitCost());
//            inventory.setProduct(p.getDescription());
//            inventory.setTotalCost(new BigDecimal(p.getTotal()).multiply(p.getUnitCost()));
//            //    productCostByMonth.setFebraury(p.getIssues());
//            System.out.println("INVENTORY " + inventory.toString());
//            inventories.add(inventory);
//        }


        return new ResponseEntity<>( reportByMonths, HttpStatus.OK);
    }


}
