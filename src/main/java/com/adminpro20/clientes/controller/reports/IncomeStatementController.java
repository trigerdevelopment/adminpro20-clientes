package com.adminpro20.clientes.controller.reports;

import com.adminpro20.clientes.model.graphic.SalesByCurrency;
import com.adminpro20.clientes.model.report.ExpenseByMonth;
import com.adminpro20.clientes.model.report.GetExpenseTypeIf;
import com.adminpro20.clientes.model.report.SalesByMonth;
import com.adminpro20.clientes.model.report.SalesByMonthIf;
import com.adminpro20.clientes.repository.bank.BankMovementRepository;
import com.adminpro20.clientes.repository.bank.ExpenseTypeRepository;
import com.adminpro20.clientes.repository.reports.SalesReportsRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/api/income-statement")
public class IncomeStatementController {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    GregorianCalendar gregorianCalendar;
    public final ExpenseTypeRepository expenseTypeRepository;
    public final BankMovementRepository bankMovementRepository;
    public final SalesReportsRepository salesReportsRepository;

    public IncomeStatementController(ExpenseTypeRepository expenseTypeRepository, BankMovementRepository bankMovementRepository, SalesReportsRepository salesReportsRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
        this.bankMovementRepository = bankMovementRepository;
        this.salesReportsRepository = salesReportsRepository;
    }

    @RequestMapping("/get-sales")
    public ResponseEntity<?> getSales(
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

        List<SalesByCurrency> salesByCurrencyList = new ArrayList<>();
           salesByCurrencyList = salesReportsRepository.getSalesByMonthGroupByMonth(2021);

        return new ResponseEntity<>(salesByCurrencyList, HttpStatus.OK);
    }

    @RequestMapping("/get-expenses")
    public ResponseEntity<?> getExpenses(
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

        List<GetExpenseTypeIf> salesByCurrencyList = new ArrayList<>();

        salesByCurrencyList = bankMovementRepository.getExpensesByMonthGroupByMonth(2018);

//     List<ExpenseByMonth> expenseByMonthList = new ArrayList<>();
//        for(int i = 1; i<13 ; i++){
//            List<GetExpenseTypeIf> getExpenseTypeIfList = bankMovementRepository.getExpenseByMonth(i, 2018);
//               for(GetExpenseTypeIf e: getExpenseTypeIfList){
//                  ExpenseByMonth expenseByMonth = new ExpenseByMonth();
//                  expenseByMonth.setExpenseName(e.getExpense());
//                  expenseByMonth.setJanuary(e.getTotal());
//                if(e.getExpense() != null){
//                    if(!e.getExpense().equalsIgnoreCase("Pago Proveedores")){
//                        expenseByMonthList.add(expenseByMonth);
//                    }
//                }
//            }
//        }
        return new ResponseEntity<>(salesByCurrencyList, HttpStatus.OK);
    }
}
