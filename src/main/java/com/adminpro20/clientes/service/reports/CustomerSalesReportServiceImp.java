package com.adminpro20.clientes.service.reports;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.repository.CustomerRepository;
import com.adminpro20.clientes.repository.reports.CustomerSalesByMonthRepository;
import com.adminpro20.clientes.repository.reports.SalesReportsRepository;
import com.adminpro20.clientes.model.CustomerSalesByMonth;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Service
public class CustomerSalesReportServiceImp implements CustomerSalesReportService{

    public final SalesReportsRepository salesReportsRepository;
    public final CustomerRepository customerRepository;
    public final CustomerSalesByMonthRepository customerSalesByMonthRepository;

    public CustomerSalesReportServiceImp(SalesReportsRepository salesReportsRepository, CustomerRepository customerRepository, CustomerSalesByMonthRepository customerSalesByMonthRepository) {
        this.salesReportsRepository = salesReportsRepository;
        this.customerRepository = customerRepository;
        this.customerSalesByMonthRepository = customerSalesByMonthRepository;
    }

    @Override
    public void addInvoiceToSalesReport(Invoice invoice) {
//        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        System.out.println("YEAR "+ year);

        GregorianCalendar calendaryear = invoice.getFecha();
        int date = calendaryear.get(Calendar.YEAR);
        System.out.println("YEAR "+ date);
      GregorianCalendar  gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        int year = gregorianCalendar.get(Calendar.YEAR);
        GregorianCalendar calendarmonth = invoice.getFecha();
        int month = calendarmonth.get(Calendar.MONTH);
        System.out.println("MONTH "+ month);


                CustomerSalesByMonth customerSalesByMonth1 = new CustomerSalesByMonth();
                BigDecimal total = salesReportsRepository.getSumTotalByCustomer(month+1,year, invoice.getCustomer().getId());

                    switch (month) {
                        case 0:
                            System.out.println("Enero");
                              customerSalesByMonth1.setJanuary(total);
//                              customerSalesByMonth1.setCustomerName(invoice.getCustomer().getCompany());
//                              customerSalesByMonth1.setSucursal(invoice.getSucursal());
                              customerSalesByMonth1.setDate(date);
//                              customerSalesByMonth1.setCustomer(customerRepository.findById(invoice.getCustomer().getId()));
                              customerSalesByMonthRepository.save(customerSalesByMonth1);
                            break;
                        case 1:
                            System.out.println("Tuesday");
                            customerSalesByMonth1.setFebruary(total);
//                            customerSalesByMonth1.setCustomerName(invoice.getCustomer().getCompany());
//                            customerSalesByMonth1.setSucursal(invoice.getSucursal());
                            customerSalesByMonth1.setDate(date);
                            if(customerRepository.existsCustomerByRfc(invoice.getCustomer().getRfc())){
//                                customerSalesByMonth1.setCustomer(customerRepository.findCustomerByRfc(invoice.getCustomer().getRfc()));
                            }
                            customerSalesByMonthRepository.save(customerSalesByMonth1);
                            break;


                    }


                }




}
