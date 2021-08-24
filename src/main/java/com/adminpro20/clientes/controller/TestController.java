package com.adminpro20.clientes.controller;


import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.graphic.SalesByCurrency;
import com.adminpro20.clientes.model.graphic.SalesByVolume;
import com.adminpro20.clientes.repository.reports.SalesByProductRepository;
import com.adminpro20.clientes.repository.reports.SalesReportsRepository;
import com.adminpro20.clientes.model.CustomerSalesByMonth;
import com.adminpro20.clientes.model.InvoiceItems;
import com.adminpro20.clientes.repository.CustomerRepository;
import com.adminpro20.clientes.repository.reports.CustomerSalesByMonthRepository;
import com.adminpro20.clientes.service.CustomerSalesByMonthIf;
import com.adminpro20.clientes.model.graphic.SalesByProduct;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/api/test")
public class TestController {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    GregorianCalendar gregorianCalendar;
    GregorianCalendar gregorianCalendar2;

    public final SalesReportsRepository salesReportsRepository;
    public final CustomerRepository customerRepository;
    public final CustomerSalesByMonthRepository customerSalesByMonthRepository;
    public final SalesByProductRepository salesByProductRepository;

    public TestController(SalesReportsRepository salesReportsRepository, CustomerRepository customerRepository, CustomerSalesByMonthRepository customerSalesByMonthRepository, SalesByProductRepository salesByProductRepository) {
        this.salesReportsRepository = salesReportsRepository;
        this.customerRepository = customerRepository;
        this.customerSalesByMonthRepository = customerSalesByMonthRepository;
        this.salesByProductRepository = salesByProductRepository;
    }

    @RequestMapping("/get-sales")
    public ResponseEntity<List<CustomerSalesByMonth>> getSales() {

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        int year = gregorianCalendar.get(Calendar.YEAR);
        System.out.println("YEAR " + year);
        List<CustomerSalesByMonth> customerSalesByMonthList= new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        customers = (List<Customer>) customerRepository.findAll();

        for(Customer c: customers){

            CustomerSalesByMonth customerSalesByMonth = new CustomerSalesByMonth();
            for(int i = 1; i<13 ; i++){

            if(salesReportsRepository.getSumTotalByCustomer(i,2020,c.getId()) !=null){
                BigDecimal total = salesReportsRepository.getSumTotalByCustomer(i,2020, c.getId());
               switch (i){
                   case 1:
                       customerSalesByMonth.setJanuary(total);
                    break;
                   case 2:
                       customerSalesByMonth.setFebruary(total);
                    break;
                   case 3:
                       customerSalesByMonth.setMarch(total);
                       break;
                   case 4:
                       customerSalesByMonth.setApril(total);
                   break;
                   case 5:
                       customerSalesByMonth.setMay(total);
                       break;
                   case 6:
                       customerSalesByMonth.setJune(total);
                       break;
                   case 7:
                       customerSalesByMonth.setJuly(total);
                       break;
                   case 8:
                       customerSalesByMonth.setAugust(total);
                       break;
                   case 9:
                       customerSalesByMonth.setSeptember(total);
                       break;
                   case 10:
                       customerSalesByMonth.setOctober(total);
                       break;
                   case 11:
                       customerSalesByMonth.setNovember(total);
                       break;
                   case 12:
                       customerSalesByMonth.setDecember(total);
                       break;
               }
                customerSalesByMonth.setCustomerName(c.getCompany());
                customerSalesByMonth.setSucursal(c.getStoreNum());
//                customerSalesByMonthList.add(customerSalesByMonth);

            }else {
                c.setBalance(BigDecimal.valueOf(0.00));
            }

            System.out.println("Customer " + c.toString());
            }
            customerSalesByMonthList.add(customerSalesByMonth);
        }

        List<CustomerSalesByMonthIf> customerSalesByMonths = salesReportsRepository.getSalesByMonth();
//        List<CustomerSalesByMonthIf> customerNoSales = salesReportsRepository.getNotSalesByMonth();
//        List<CustomerSalesByMonth> customerSalesByMonthArrayList = new ArrayList<>();
        for(CustomerSalesByMonthIf p : customerSalesByMonths) {
            CustomerSalesByMonth customerSalesByMonth = new CustomerSalesByMonth();

             customerSalesByMonth.setSales(p.getTotal());
             customerSalesByMonth.setCustomerName(p.getCompany());
             customerSalesByMonth.setSucursal(p.getSucursal());
//             customerSalesByMonthArrayList.add(customerSalesByMonth);
//            inventory.setQuantity(p.getTotal());
//            inventory.setUnitCost(p.getUnitCost());
//            inventory.setProduct(p.getDescription());
//            inventory.setTotalCost(new BigDecimal(p.getTotal()).multiply(p.getUnitCost()));
//            //    productCostByMonth.setFebraury(p.getIssues());
//            System.out.println("INVENTORY " + inventory.toString());
//            inventories.add(inventory);
        }


        return new ResponseEntity<>( customerSalesByMonthList, HttpStatus.OK);
    }



    @RequestMapping(value = "/get-sales-by-query",method = RequestMethod.GET)
    public ResponseEntity<List<CustomerSalesByMonth>> getSalesByQuery(

            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String sucursal,
            @RequestParam(required = false, defaultValue = "byMonth") String report

    ) {
        List<CustomerSalesByMonth> customerSalesByMonthList= new ArrayList<>();
        System.out.println("REPORTE " + report);
        switch (report) {

            case "byMonth":
                System.out.println("TIPO DE REPORTE " + "byMonth");
                break;
            case "byWeek":
                System.out.println("TIPO DE REPORTE " + "byWeek");
            customerSalesByMonthList = getSalesByWeek(iniDate,finalDate);

                break;
        }



//        CustomerSalesByMonth customerSalesByMonth = new CustomerSalesByMonth();
//        List<Customer> customers = new ArrayList<>();
//        customers = (List<Customer>) customerRepository.findAll();
//
//        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
//        if(iniDate !=null){
//            gregorianCalendar.setTime(iniDate);
//        }else {
//            gregorianCalendar = null;
//        }
//        gregorianCalendar2 = (GregorianCalendar) Calendar.getInstance();
//        if (finalDate !=null){
//            gregorianCalendar2.setTime(finalDate);
//        }else {
//            gregorianCalendar2 = null;
//        }
//        System.out.println("FECHA 1 " + gregorianCalendar.toString() );
//        System.out.println("FECHA 2 " + gregorianCalendar2.toString() );
//
//          int monthini =  gregorianCalendar.get(Calendar.WEEK_OF_MONTH);
//          int monthfin =  gregorianCalendar2.get(Calendar.WEEK_OF_MONTH);
//        System.out.println("MONTH INI " + monthini);
//        System.out.println("MONTH FINAL " + monthfin);

//        System.out.println("FECHA INICIAL MAS UNO " + gregorianCalendar.add(Calendar.DAY_OF_MONTH,1));
//        gregorianCalendar.add(Calendar.DAY_OF_MONTH,1);
//        System.out.println("FECHA MAS UN DIA " + gregorianCalendar);
////            System.out.println("VALORES DE I "  +  i);
//            HashMap<Integer, BigDecimal> capitalCities = new HashMap<Integer, BigDecimal>();
//
//        for (Customer c : customers) {
//            CustomerSalesByMonth customerSalesByMonth = new CustomerSalesByMonth();
//            BigDecimal total = salesReportsRepository.getSumTotalByCustomerByWeek(gregorianCalendar, gregorianCalendar2, c.getId());
//
//            customerSalesByMonth.setId(c.getId());
//            customerSalesByMonth.setCustomerName(c.getCompany());
//            customerSalesByMonth.setSucursal(c.getStoreNum());
//            customerSalesByMonth.setJanuary(total);
////            capitalCities.put(i, total);
////            customerSalesByMonth.setSalesReport(capitalCities);
//            customerSalesByMonthList.add(customerSalesByMonth);
//
//    }




//
//        System.out.println("MONTH " + (monthfin - monthini));
//        for(Customer c: customers){
//
//
//                if(salesReportsRepository.getSumTotalByCustomerByQuery(gregorianCalendar, gregorianCalendar2, inFolio, finFolio, company,sucursal,new BigDecimal(total1), new BigDecimal(total2),c.getId()) !=null){
//                    BigDecimal total = salesReportsRepository.getSumTotalByCustomerByQuery(gregorianCalendar, gregorianCalendar2, inFolio, finFolio, company,sucursal,new BigDecimal(total1), new BigDecimal(total2),c.getId());
//
//                    customerSalesByMonth.setCustomerName(c.getCompany());
//                    customerSalesByMonth.setSucursal(c.getStoreNum());
////                customerSalesByMonthList.add(customerSalesByMonth);
//
//                }else {
//                    c.setBalance(BigDecimal.valueOf(0.00));
//                }
//
//                System.out.println("Customer " + c.toString());
//
//            customerSalesByMonthList.add(customerSalesByMonth);
//        }

//        List<CustomerSalesByMonthIf> customerSalesByMonths = salesReportsRepository.getSalesByMonth();
////        List<CustomerSalesByMonthIf> customerNoSales = salesReportsRepository.getNotSalesByMonth();
////        List<CustomerSalesByMonth> customerSalesByMonthArrayList = new ArrayList<>();
//        for(CustomerSalesByMonthIf p : customerSalesByMonths) {
//            CustomerSalesByMonth customerSalesByMonth = new CustomerSalesByMonth();
//
//            customerSalesByMonth.setSales(p.getTotal());
//            customerSalesByMonth.setCustomerName(p.getCompany());
//            customerSalesByMonth.setSucursal(p.getSucursal());
////             customerSalesByMonthArrayList.add(customerSalesByMonth);
////            inventory.setQuantity(p.getTotal());
////            inventory.setUnitCost(p.getUnitCost());
////            inventory.setProduct(p.getDescription());
////            inventory.setTotalCost(new BigDecimal(p.getTotal()).multiply(p.getUnitCost()));
////            //    productCostByMonth.setFebraury(p.getIssues());
////            System.out.println("INVENTORY " + inventory.toString());
////            inventories.add(inventory);
//        }

        System.out.println("QUE TAL PASAMOS POR AQUI ......");
        return new ResponseEntity<>( customerSalesByMonthList, HttpStatus.OK);
    }

//    @RequestMapping(value = "/get-sales-by-day",method = RequestMethod.GET)
    public List<CustomerSalesByMonth> getSalesByWeek(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate
              ) {


        List<CustomerSalesByMonth> customerSalesByMonthList = new ArrayList<>();
//        CustomerSalesByMonth customerSalesByMonth = new CustomerSalesByMonth();
        List<Customer> customers = new ArrayList<>();
        customers = (List<Customer>) customerRepository.findAll();

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        GregorianCalendar gregorianCalendarVar;


        if (iniDate != null) {
            gregorianCalendar.setTime(iniDate);
        } else {
            gregorianCalendar = null;
        }
        gregorianCalendar2 = (GregorianCalendar) Calendar.getInstance();
        if (finalDate != null) {
            gregorianCalendar2.setTime(finalDate);
        } else {
            gregorianCalendar2 = null;
        }
        System.out.println("FECHA 1 " + gregorianCalendar.toString());
        System.out.println("FECHA 2 " + gregorianCalendar2.toString());

        int monthini = gregorianCalendar.get(Calendar.WEEK_OF_MONTH);
        int monthfin = gregorianCalendar2.get(Calendar.WEEK_OF_MONTH);
        System.out.println("MONTH INI " + monthini);
        System.out.println("MONTH FINAL " + monthfin);

//        System.out.println("FECHA INICIAL MAS UNO " + gregorianCalendar.add(Calendar.DAY_OF_MONTH,1));
//        gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
//        gregorianCalendar.add(Calendar.WEEK_OF_MONTH, 2);
        System.out.println("FECHA MAS UN DIA " + gregorianCalendar);
        System.out.println("FECHA MAS UNA SEMANA " + gregorianCalendar);
//            System.out.println("VALORES DE I "  +  i);
        HashMap<Integer, BigDecimal> capitalCities = new HashMap<Integer, BigDecimal>();

        for (Customer c : customers) {


                CustomerSalesByMonth customerSalesByMonth = new CustomerSalesByMonth();
                BigDecimal total = salesReportsRepository.getSumTotalByCustomerByWeek(gregorianCalendar, gregorianCalendar2, c.getId());

                customerSalesByMonth.setId(c.getId());
                customerSalesByMonth.setCustomerName(c.getCompany());
                customerSalesByMonth.setSucursal(c.getStoreNum());
                customerSalesByMonth.setSales(total);
//            capitalCities.put(i, total);
//            customerSalesByMonth.setSalesReport(capitalCities);
                customerSalesByMonthList.add(customerSalesByMonth);
            }

        return customerSalesByMonthList;
    }

    @GetMapping(value = "/getSalesByProduct")
//    @RequestMapping(value = "/getSalesByProduct", method = RequestMethod.GET)
    public ResponseEntity<List<SalesByVolume>> readBankTransactions(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = true) Long id

            ) {

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        if(iniDate !=null){
            gregorianCalendar.setTime(iniDate);
        }else {
            gregorianCalendar = null;
        }
        gregorianCalendar2 = (GregorianCalendar) Calendar.getInstance();
        if (finalDate !=null){
            gregorianCalendar2.setTime(finalDate);
        }else {
            gregorianCalendar2 = null;
        }

            List<InvoiceItems> salesByProductList = salesByProductRepository.getInvoicesItem(1L);
            ArrayList<InvoiceItems> invoiceItems = new ArrayList<>();
            for (InvoiceItems s : salesByProductList) {
                System.out.println("INVOICE ITEMS " + "  "+ s.getId() + s.getDescripcion() + "  " + s.getCantidad());
            }
        List<SalesByProduct> invoiceItems1 = salesByProductRepository.getInvoicesItem2(2L);

            ArrayList<SalesByProduct> salesByProducts = new ArrayList<>();
            for(SalesByProduct s: invoiceItems1){
                System.out.println("CANTIDAD " + ": " +  s.getCantidad() + ", " + "Clave Unidad " + s.getClaveUnidad() + ", "+s.getDescripcion() );
                salesByProducts.add(s);
            }

            List<SalesByVolume> salesByVolumes = salesByProductRepository.getSalesByVolume( gregorianCalendar,gregorianCalendar2,id);

            ArrayList<SalesByVolume> salesByVolumes1 = new ArrayList<>();
            for(SalesByVolume p: salesByVolumes){
                System.out.println("CANTIDAD " + ": " + p.getName()  + ", " + "Clave Unidad " + p.getValue() );
                salesByVolumes1.add(p);
            }

        return new ResponseEntity<List<SalesByVolume>>(salesByVolumes1, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/getSalesByMonth")
//    @RequestMapping(value = "/getSalesByProduct", method = RequestMethod.GET)
    public ResponseEntity<List<SalesByCurrency>> readBankTransactions(){

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        int year = gregorianCalendar.get(Calendar.YEAR);
        List<SalesByCurrency> salesByCurrencies = salesReportsRepository.getSalesByMonthGroupByMonth(year);
        List<SalesByCurrency> sales = new ArrayList<>();
        for(SalesByCurrency v : salesByCurrencies) {
//            System.out.println("Name " + v.getName() + "Value " + v.getValue());
            v.getName();
            v.getValue();
            sales.add(v);
        }

        return new ResponseEntity<>(sales, HttpStatus.OK);


    }


    @GetMapping(value = "/get-sales-by-month-query")
    public ResponseEntity<List<SalesByCurrency>> getSalesByMonthQuery(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate
    ){

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        int year = gregorianCalendar.get(Calendar.YEAR);
        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        if(iniDate !=null){
            gregorianCalendar.setTime(iniDate);
        }else {
            gregorianCalendar = null;
        }
        gregorianCalendar2 = (GregorianCalendar) Calendar.getInstance();
        if (finalDate !=null){
            gregorianCalendar2.setTime(finalDate);
            gregorianCalendar2.add(Calendar.DAY_OF_MONTH, 1);
        }else {
            gregorianCalendar2 = null;
        }


        List<SalesByCurrency> salesByCurrencies = salesReportsRepository.getSalesByMonthGroupByDates(gregorianCalendar, gregorianCalendar2);
        List<SalesByCurrency> sales = new ArrayList<>();
        for(SalesByCurrency v : salesByCurrencies) {
            System.out.println("Name " + v.getName() + "Value " + v.getValue());
            sales.add(v);
        }

        return new ResponseEntity<>(sales, HttpStatus.OK);


    }

}
