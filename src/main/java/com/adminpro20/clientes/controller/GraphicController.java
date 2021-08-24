package com.adminpro20.clientes.controller;


import com.adminpro20.clientes.repository.reports.SalesReportsRepository;
import com.adminpro20.clientes.model.graphic.SalesByCurrency;
import com.adminpro20.clientes.repository.graphics.GraphicsReportRepository;
import com.adminpro20.clientes.model.graphic.SalesByVolume;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/api/graphic")
public class GraphicController {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    GregorianCalendar gregorianCalendar;
    GregorianCalendar gregorianCalendar2;

    public final GraphicsReportRepository graphicsReportRepository;
    public final SalesReportsRepository salesReportsRepository;

    public GraphicController(GraphicsReportRepository graphicsReportRepository, SalesReportsRepository salesReportsRepository) {
        this.graphicsReportRepository = graphicsReportRepository;
        this.salesReportsRepository = salesReportsRepository;
    }


    /**********************************************************************************************************************************************
     *                             REGRESA EL VOLUMEN DE VENTAS POR PRODUCTO DURANTE EL AÑO EN CURSO
     ***********************************************************************************************************************************************/

    @RequestMapping(value = "/products-volume", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<SalesByVolume>> getSalesVolume() {
        System.out.println("ENTRADA A PRODUCTS VOLUME ");

        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);

        List<SalesByVolume> salesByVolumes = graphicsReportRepository.getSalesByVolumeThisYear(year);

        ArrayList<SalesByVolume> salesByVolumes1 = new ArrayList<>();
        for (SalesByVolume p : salesByVolumes) {
            System.out.println("CANTIDAD " + ": " + p.getName() + ", " + "Clave Unidad " + p.getValue());
            salesByVolumes1.add(p);
        }

        return new ResponseEntity<List<SalesByVolume>>(salesByVolumes1, HttpStatus.OK);
    }


    /************************************************************************************************************************************************
     *                                         RETURN TOTAL SALES VOLUME OF EACH PRODUCT BETWEEN DATES
     *************************************************************************************************************************************************/

    @RequestMapping(value = "/products-volume-by-date", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<SalesByVolume>> salesByVolumeBetweenDates(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String sucursal

            ) {

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
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

        List<SalesByVolume> salesByVolumes = graphicsReportRepository.getSalesByVolumeBetweenDates(gregorianCalendar, gregorianCalendar2,company,sucursal);

        ArrayList<SalesByVolume> salesByVolumes1 = new ArrayList<>();
        for (SalesByVolume p : salesByVolumes) {
            p.getName();
            p.getValue();
            salesByVolumes1.add(p);
        }

        return new ResponseEntity<List<SalesByVolume>>(salesByVolumes1, HttpStatus.OK);
    }

    /**********************************************************************************************************************************************
     *                             REGRETS LAS SALES POR MES CURRENCY DEL AÑO EN CURSO                                                                    *
     ***********************************************************************************************************************************************/

    @GetMapping(value = "/sales-month")
    public ResponseEntity<List<SalesByCurrency>> readBankTransactions() {

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        int year = gregorianCalendar.get(Calendar.YEAR);
        List<SalesByCurrency> salesByCurrencies = salesReportsRepository.getSalesByMonthGroupByMonth(year);
        List<SalesByCurrency> sales = new ArrayList<>();
        for (SalesByCurrency v : salesByCurrencies) {
            v.getName();
            v.getValue();
            sales.add(v);
        }
        System.out.println("REGRESO DEL SALES-MONTH");
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    /**********************************************************************************************************************************************
     *                             REGRETS LAS VENTAS POR MES EN PESOS ENTRE FECHAS                                                                    *
     ***********************************************************************************************************************************************/

    @GetMapping(value = "/sales-month-by-dates")
    public ResponseEntity<List<SalesByCurrency>> getSalesByMonthQuery(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String sucursal
    ) {

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        int year = gregorianCalendar.get(Calendar.YEAR);
        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        if (iniDate != null) {
            gregorianCalendar.setTime(iniDate);
        } else {
            gregorianCalendar = null;
        }
        gregorianCalendar2 = (GregorianCalendar) Calendar.getInstance();
        if (finalDate != null) {
            gregorianCalendar2.setTime(finalDate);
            gregorianCalendar2.add(Calendar.DAY_OF_MONTH, 1);
        } else {
            gregorianCalendar2 = null;
        }


        List<SalesByCurrency> salesByCurrencies = graphicsReportRepository.getSalesByMonthGroupByDates(gregorianCalendar, gregorianCalendar2,company,sucursal);
        List<SalesByCurrency> sales = new ArrayList<>();
        for (SalesByCurrency v : salesByCurrencies) {
            System.out.println("Name " + v.getName() + "Value " + v.getValue());
            sales.add(v);
        }

        return new ResponseEntity<>(sales, HttpStatus.OK);


    }


    /**********************************************************************************************************************************************
     *                             REGRESA EL VOLUMEN DE VENTAS POR PRODUCTO ENTRE FECHAS POR CLENTE ID                                              *
     ***********************************************************************************************************************************************/
    @RequestMapping(value = "/products-volume-date-customer", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<SalesByVolume>> readBankTransactions(
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date iniDate,
            @RequestParam(required = false, defaultValue = "2100-01-01") @DateTimeFormat(pattern = DATE_PATTERN) Date finalDate,
            @RequestParam(required = false) Long id

    ) {

        gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
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

        List<SalesByVolume> salesByVolumes = graphicsReportRepository.getSalesByVolumeBetweenDatesAndCustomerId(gregorianCalendar, gregorianCalendar2, id);

        ArrayList<SalesByVolume> salesByVolumes1 = new ArrayList<>();
        for (SalesByVolume p : salesByVolumes) {
            System.out.println("CANTIDAD " + ": " + p.getName() + ", " + "Clave Unidad " + p.getValue());
            salesByVolumes1.add(p);
        }

        return new ResponseEntity<List<SalesByVolume>>(salesByVolumes1, HttpStatus.OK);
    }



}
