package com.adminpro20.clientes.controller;


import com.adminpro20.clientes.model.Customer;
import com.adminpro20.clientes.model.CustomerSalesByMonth;
import com.adminpro20.clientes.model.SalesCostByMonth;
import com.adminpro20.clientes.repository.CustomerRepository;
import com.adminpro20.clientes.repository.inventory.MovementsWharehouseRepository;
import com.adminpro20.clientes.repository.reports.CustomerSalesByMonthRepository;
import com.adminpro20.clientes.repository.reports.SalesByProductRepository;
import com.adminpro20.clientes.repository.reports.SalesReportsRepository;
import com.adminpro20.clientes.service.CustomerSalesByMonthIf;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("api/report")
public class SalesReportController {


    public static final String DATE_PATTERN = "yyyy-MM-dd";
    GregorianCalendar gregorianCalendar;
    GregorianCalendar gregorianCalendar2;

    public final SalesReportsRepository salesReportsRepository;
    public final CustomerRepository customerRepository;
    public final CustomerSalesByMonthRepository customerSalesByMonthRepository;
    public final SalesByProductRepository salesByProductRepository;
    public final MovementsWharehouseRepository movementsWharehouseRepository;

//    public TestController(SalesReportsRepository salesReportsRepository, CustomerRepository customerRepository, CustomerSalesByMonthRepository customerSalesByMonthRepository, SalesByProductRepository salesByProductRepository) {
//        this.salesReportsRepository = salesReportsRepository;
//        this.customerRepository = customerRepository;
//        this.customerSalesByMonthRepository = customerSalesByMonthRepository;
//        this.salesByProductRepository = salesByProductRepository;
//    }

    HttpHeaders headers = new HttpHeaders();


    public SalesReportController(SalesReportsRepository salesReportsRepository, CustomerRepository customerRepository, CustomerSalesByMonthRepository customerSalesByMonthRepository, SalesByProductRepository salesByProductRepository, MovementsWharehouseRepository movementsWharehouseRepository) {
        this.salesReportsRepository = salesReportsRepository;
        this.customerRepository = customerRepository;
        this.customerSalesByMonthRepository = customerSalesByMonthRepository;
        this.salesByProductRepository = salesByProductRepository;
        this.movementsWharehouseRepository = movementsWharehouseRepository;
    }

    @RequestMapping("/get-sales")
    public ResponseEntity<List<CustomerSalesByMonth>> getSales(
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

        List<CustomerSalesByMonth> customerSalesByMonthList= new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        customers = (List<Customer>) customerRepository.findAll();

        for(Customer c: customers){

            CustomerSalesByMonth customerSalesByMonth = new CustomerSalesByMonth();
            for(int i = 1; i<13 ; i++){

                if(salesReportsRepository.getSumTotalByCustomer(i,year,c.getId()) !=null){
                    BigDecimal total = salesReportsRepository.getSumTotalByCustomer(i,year, c.getId());
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
                    customerSalesByMonth.setId(c.getId());
                    customerSalesByMonth.setCustomerName(c.getCompany());
                    customerSalesByMonth.setSucursal(c.getStoreNum());
//                customerSalesByMonthList.add(customerSalesByMonth);

                }else {

                    switch (i) {
                        case 1:
                          customerSalesByMonth.setJanuary(BigDecimal.valueOf(0.00));
                          break;
                          case 2:
                          customerSalesByMonth.setFebruary(BigDecimal.valueOf(0.00));
                          break;
                          case 3:
                          customerSalesByMonth.setMarch(BigDecimal.valueOf(0.00));
                          break;
                          case 4:
                          customerSalesByMonth.setApril(BigDecimal.valueOf(0.00));
                          break;
                          case 5:
                          customerSalesByMonth.setMay(BigDecimal.valueOf(0.00));
                          break;
                          case 6:
                          customerSalesByMonth.setJune(BigDecimal.valueOf(0.00));
                          break;
                          case 7:
                          customerSalesByMonth.setJuly(BigDecimal.valueOf(0.00));
                          break;
                          case 8:
                          customerSalesByMonth.setAugust(BigDecimal.valueOf(0.00));
                          break;
                          case 9:
                          customerSalesByMonth.setSeptember(BigDecimal.valueOf(0.00));
                          break;
                          case 10:
                          customerSalesByMonth.setOctober(BigDecimal.valueOf(0.00));
                          break;
                          case 11:
                          customerSalesByMonth.setNovember(BigDecimal.valueOf(0.00));
                          break;
                          case 12:
                          customerSalesByMonth.setDecember(BigDecimal.valueOf(0.00));
                          break;
                    }

                    customerSalesByMonth.setId(c.getId());
                    customerSalesByMonth.setCustomerName(c.getCompany());
                    customerSalesByMonth.setSucursal(c.getStoreNum());
                    c.setBalance(BigDecimal.valueOf(0.00));
                }

//                System.out.println("Customer " + c.toString());
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


    @RequestMapping("/get-sales-cost")
    public ResponseEntity<?> getSalesCostByMonth(
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

//        List<SalesCostByMonth> salesCostByMonths = new ArrayList<>();
        SalesCostByMonth salesCostByMonth = new SalesCostByMonth();
            for(int i = 1; i<13 ; i++){


                    BigDecimal total = movementsWharehouseRepository.getSalesCostByMonth(i,year);
                    if(total !=null){

                    switch (i){
                        case 1:
                            salesCostByMonth.setJanuary(total);
                            break;
                        case 2:
                            salesCostByMonth.setFebruary(total);
                            break;
                        case 3:
                            salesCostByMonth.setMarch(total);
                            break;
                        case 4:
                            salesCostByMonth.setApril(total);
                            break;
                        case 5:
                            salesCostByMonth.setMay(total);
                            break;
                        case 6:
                            salesCostByMonth.setJune(total);
                            break;
                        case 7:
                            salesCostByMonth.setJuly(total);
                            break;
                        case 8:
                            salesCostByMonth.setAugust(total);
                            break;
                        case 9:
                            salesCostByMonth.setSeptember(total);
                            break;
                        case 10:
                            salesCostByMonth.setOctober(total);
                            break;
                        case 11:
                            salesCostByMonth.setNovember(total);
                            break;
                        case 12:
                            salesCostByMonth.setDecember(total);
                            break;
                }
                    }else {
                        switch (i) {
                            case 1:
                                salesCostByMonth.setJanuary(new BigDecimal(0.00));
                                break;
                            case 2:
                                salesCostByMonth.setFebruary(new BigDecimal(0.00));
                                break;
                            case 3:
                                salesCostByMonth.setMarch(new BigDecimal(0.00));
                                break;
                            case 4:
                                salesCostByMonth.setApril(new BigDecimal(0.00));
                                break;
                            case 5:
                                salesCostByMonth.setMay(new BigDecimal(0.00));
                                break;
                            case 6:
                                salesCostByMonth.setJune(new BigDecimal(0.00));
                                break;
                            case 7:
                                salesCostByMonth.setJuly(new BigDecimal(0.00));
                                break;
                            case 8:
                                salesCostByMonth.setAugust(new BigDecimal(0.00));
                                break;
                            case 9:
                                salesCostByMonth.setSeptember(new BigDecimal(0.00));
                                break;
                            case 10:
                                salesCostByMonth.setOctober(new BigDecimal(0.00));
                                break;
                            case 11:
                                salesCostByMonth.setNovember(new BigDecimal(0.00));
                                break;
                            case 12:
                                salesCostByMonth.setDecember(new BigDecimal(0.00));
                                break;

                        }
                    }


            }

        return new ResponseEntity<>( salesCostByMonth, HttpStatus.OK);
    }




    @RequestMapping(value = "/get-sales-by-month", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, String>> getTotalsByMonth(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String sucursal
    ){
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        HashMap<String, String> ventaPorMes = new HashMap<String, String>();
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

        for (int i = 0; i < 12; i++) {
            BigDecimal total = salesReportsRepository.getSumTotalByMonth(i,year);
            if(total !=null){
                switch (i) {
                    case 1:
                        System.out.println("Enero");
                        ventaPorMes.put("Enero", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 2:
                        System.out.println("Tuesday");
                        ventaPorMes.put("Febrero", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 3:
                        System.out.println("Marzo");
                        ventaPorMes.put("Marzo", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 4:
                        System.out.println("Marzo");
                        ventaPorMes.put("Abril", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 5:
                        System.out.println("Marzo");
                        ventaPorMes.put("Mayo", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 6:
                        System.out.println("Marzo");
                        ventaPorMes.put("Junio", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 7:
                        System.out.println("Marzo");
                        ventaPorMes.put("Julio", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 8:
                        System.out.println("Marzo");
                        ventaPorMes.put("Agosto", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 9:
                        System.out.println("Marzo");
                        ventaPorMes.put("Septiembre", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 10:
                        System.out.println("Marzo");
                        ventaPorMes.put("Octubre", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 11:
                        System.out.println("Marzo");
                        ventaPorMes.put("Noviembre", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 12:
                        System.out.println("Marzo");
                        ventaPorMes.put("Diciembre", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                }


            }
            System.out.println(" Sales by Month " + total);
        }

        System.out.println("VENTAS DEL AÑO " + ventaPorMes);
//        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        int date = calendar.get(Calendar.MONTH) +1;
//        int year = calendar.get(Calendar.YEAR);
        System.out.println("Date "+ date);
        System.out.println("YEAR  "+ year);
        System.out.println("Calendar "+ calendar.toString());
        System.out.println("SALES " );
        BigDecimal total = salesReportsRepository.getSumTotalByDate(gregorianCalendar, gregorianCalendar2, company, sucursal);
        System.out.println("TOTAL DE VENTA "+ total);

        return new ResponseEntity<>(ventaPorMes, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-sales-by-date", method = RequestMethod.GET)
    public ResponseEntity<String> getSalesByMonth(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String sucursal
    ){

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

        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        int date = calendar.get(Calendar.MONTH) +1;
        System.out.println("Date "+ date);
        System.out.println("Calendar "+ calendar.toString());
        System.out.println("SALES " );
        if(salesReportsRepository.getSumTotalByDate(gregorianCalendar,gregorianCalendar2,company,sucursal) != null){
            BigDecimal total = salesReportsRepository.getSumTotalByDate(gregorianCalendar, gregorianCalendar2,company,sucursal);
            System.out.println("TOTAL DE VENTA "+ total);

            return new ResponseEntity<>(total.toString(), headers, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("no existen datos", headers, HttpStatus.NO_CONTENT);
        }


    }

    @RequestMapping(value = "/get-sales-current-date", method = RequestMethod.GET)
    public ResponseEntity<String> getSalesByCurrentDate(){

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();

        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        int date = calendar.get(Calendar.MONTH) +1;
        int year = calendar.get(Calendar.YEAR);
        System.out.println("Date "+ date);
        System.out.println("Calendar "+ calendar.toString());
        System.out.println("SALES " );
        BigDecimal total = salesReportsRepository.getSumTotalByMonth(date, year);
        System.out.println("TOTAL DE VENTA "+ total);
        if(total !=null){
            return new ResponseEntity<>(total.toString(), headers, HttpStatus.OK);

        }
    return new ResponseEntity<>("no existen datos", headers, HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/sales-by-month", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, String>> SalesByMonth(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String sucursal
    ){
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        HashMap<String, String> ventaPorMes = new HashMap<String, String>();
        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        int month = 1;
        int month2 =12;

        if(iniDate !=null){
            gregorianCalendar.setTime(iniDate);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(iniDate);
            month = calendar1.get(Calendar.MONTH);


        }else {
            gregorianCalendar = null;
        }
        gregorianCalendar2 = (GregorianCalendar) Calendar.getInstance();
        if (finalDate !=null){
            gregorianCalendar2.setTime(finalDate);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finalDate);
            month2 = calendar2.get(Calendar.MONTH);
        }else {
            gregorianCalendar2 = null;
        }

        System.out.println("MES 1 " + month + " " + "MES2 " + month2);

        for (int i = 0; i < 12; i++) {
            BigDecimal total = salesReportsRepository.getSumTotalByMonth(i,year);
            if(total !=null){
                switch (i) {
                    case 1:
                        System.out.println("Enero");
                        ventaPorMes.put("Enero", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 2:
                        System.out.println("Tuesday");
                        ventaPorMes.put("Febrero", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 3:
                        System.out.println("Marzo");
                        ventaPorMes.put("Marzo", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 4:
                        System.out.println("Marzo");
                        ventaPorMes.put("Abril", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 5:
                        System.out.println("Marzo");
                        ventaPorMes.put("Mayo", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 6:
                        System.out.println("Marzo");
                        ventaPorMes.put("Junio", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 7:
                        System.out.println("Marzo");
                        ventaPorMes.put("Julio", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 8:
                        System.out.println("Marzo");
                        ventaPorMes.put("Agosto", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 9:
                        System.out.println("Marzo");
                        ventaPorMes.put("Septiembre", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 10:
                        System.out.println("Marzo");
                        ventaPorMes.put("Octubre", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 11:
                        System.out.println("Marzo");
                        ventaPorMes.put("Noviembre", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                    case 12:
                        System.out.println("Marzo");
                        ventaPorMes.put("Diciembre", salesReportsRepository.getSumTotalByMonth(i,year).toString());
                        break;
                }


            }
            System.out.println(" Sales by Month " + total);
        }

        System.out.println("VENTAS DEL AÑO " + ventaPorMes);
//        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        int date = calendar.get(Calendar.MONTH) +1;
//        int year = calendar.get(Calendar.YEAR);
        System.out.println("Date "+ date);
        System.out.println("YEAR  "+ year);
        System.out.println("Calendar "+ calendar.toString());
        System.out.println("SALES " );
        BigDecimal total = salesReportsRepository.getSumTotalByDate(gregorianCalendar, gregorianCalendar2, company, sucursal);
        System.out.println("TOTAL DE VENTA "+ total);

        return new ResponseEntity<>(ventaPorMes, headers, HttpStatus.OK);
    }

}
