package vsparcheuskaya.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vsparcheuskaya.database.Controller;
import vsparcheuskaya.entities.Order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class ApatchPOIClass {
    private final static String xlsFile = "files/ordersbook.xlsx";

    public static void writeXLSX(List<Order> orderList) throws IOException {
        FileOutputStream out = new FileOutputStream(new File(xlsFile)); //создаем файл
        XSSFWorkbook workbook = new XSSFWorkbook();     //создаем рабочую книгу
        XSSFSheet sheet = workbook.createSheet("orders");   //создать лист
        int rowindex = 0 ;
        XSSFRow row = sheet.createRow((short) rowindex);
        row.createCell(0).setCellValue("Order ID");
        row.createCell(1).setCellValue("Order name");
        row.createCell(2).setCellValue("Order date");
        for (Order order: orderList) {
            row = sheet.createRow((short) ++rowindex);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getName());
            row.createCell(2).setCellValue(order.getDate());
        }
        workbook.write(out);
        out.close();
    }

    public static void readXLSX() throws IOException {
        try(FileInputStream file = new FileInputStream(new File(xlsFile));){
            XSSFWorkbook workbook = new XSSFWorkbook(file); //создаем экземпляр Workbook
            XSSFSheet sheetcurr = workbook.getSheetAt(0);   //получить первый sheet из workbook
            Iterator<Row> rowIterator = sheetcurr.iterator();   //создаем итератор по рядам
            if(rowIterator.hasNext())   //skip первую строку, где заголовки
                rowIterator.next();
            while (rowIterator.hasNext()) {
                Row rowcurr = rowIterator.next();
                Iterator<Cell> cellIterator = rowcurr.cellIterator(); //для каждого row создаем итератор по колонкам
                while (cellIterator.hasNext()) {
                    Cell cellcurr = cellIterator.next();
                    switch (cellcurr.getColumnIndex()) {
                        case 0:
                            System.out.print((int)cellcurr.getNumericCellValue() + "\t");
                            break;
                        case 1:
                            System.out.print(cellcurr.getStringCellValue() + "\t");
                            break;
                        case 2:
                            System.out.print(cellcurr.getLocalDateTimeCellValue().toLocalDate() + "\n");
                            break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        List<Order> orderList = new Controller().getOrders();
        writeXLSX(orderList);
        readXLSX();

    }
}
