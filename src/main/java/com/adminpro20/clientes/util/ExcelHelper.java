package com.adminpro20.clientes.util;

import com.adminpro20.clientes.model.bank.BankAccount;
import com.adminpro20.clientes.model.bank.BankMovementCsv;
import com.adminpro20.clientes.model.bank.BankingTransactions;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = { "id", "name", "account"};
    static String SHEET = "Accounts";
    static String BANKING_TRANS = "MovimientosBancarios";

    public static boolean hasExcelFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());

    }

    public static List<BankAccount> excelToBankAccount(InputStream is) throws IOException {
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheet(SHEET);
        Iterator<Row> rows = sheet.iterator();
        List<BankAccount> tutorials = new ArrayList<BankAccount>();

        int rowNumber = 0;
        while (rows.hasNext()) {
            Row currentRow = rows.next();

            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }

            Iterator<Cell> cellsInRow = currentRow.iterator();

            BankAccount tutorial = new BankAccount();

            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();

                switch (cellIdx) {
                    case 0:
                       tutorial.setId((long) currentCell.getNumericCellValue());
                        break;

                    case 1:
                        tutorial.setName(currentCell.getStringCellValue());
                        break;

                    case 2:
                        tutorial.setAccount(currentCell.getStringCellValue());
                        break;
                    case 3:
                        tutorial.setDate(currentCell.getDateCellValue());
                        break;
//                        case 3:
//                            tutorial.setPublished(currentCell.getBooleanCellValue());
//                            break;

                    default:
                        break;
                }

                cellIdx++;
            }

            tutorials.add(tutorial);
        }

        workbook.close();

        return tutorials;
    }

    public static List<BankingTransactions> excelToBankMovements(InputStream is) throws IOException {
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheet(BANKING_TRANS);
        Iterator<Row> rows = sheet.iterator();
        List<BankingTransactions> bankMovementCsvList = new ArrayList<BankingTransactions>();

        int rowNumber = 0;
        while (rows.hasNext()) {
            Row currentRow = rows.next();

            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }

            Iterator<Cell> cellsInRow = currentRow.iterator();

            BankingTransactions bankingTransactions = new BankingTransactions();

            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();

                switch (cellIdx) {
                    case 0:
                        bankingTransactions.setId((long) currentCell.getNumericCellValue());
                        break;
                    case 1:
                        bankingTransactions.setCuenta((long) currentCell.getNumericCellValue());
                        break;
                    case 2:
                        bankingTransactions.setDateTransactions(currentCell.getDateCellValue());
                        break;

                    case 3:
                        bankingTransactions.setDate(currentCell.getDateCellValue());
                        break;
                    case 4:
                        bankingTransactions.setReference((long) currentCell.getNumericCellValue());
                        break;
                    case 5:
                        bankingTransactions.setDescription(currentCell.getStringCellValue());
                        break;
                    case 6:
                        bankingTransactions.setCodeTransaction((long) currentCell.getNumericCellValue());
                        break;
                    case 7:
                        bankingTransactions.setSucursal((long) currentCell.getNumericCellValue());
                        break;
                    case 8:
                        bankingTransactions.setDeposit(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                        break;
                    case 9:
                        bankingTransactions.setBankWithdrawals(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                        break;
                    case 10:
                        bankingTransactions.setBalance(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                        break;
                    case 11:
                        bankingTransactions.setMovimiento((long) currentCell.getNumericCellValue());
                        break;
                    case 12:
                        bankingTransactions.setDetails(currentCell.getStringCellValue());
                        break;

//                        case 3:
//                            tutorial.setPublished(currentCell.getBooleanCellValue());
//                            break;

                    default:
                        break;
                }

                cellIdx++;
            }

            bankMovementCsvList.add(bankingTransactions);
        }

        workbook.close();

        return bankMovementCsvList;
    }
}
