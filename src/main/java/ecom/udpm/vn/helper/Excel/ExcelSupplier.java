package ecom.udpm.vn.helper.Excel;

import ecom.udpm.vn.entity.Supplier;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelSupplier {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = { "Code", "Name", "Email","Phone","Address","Created date","Modify date","Is delete","Status transaction" };
    static String SHEET = "Suppliers";
    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
    public static List<Supplier> excelToSuppliers(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Supplier> suppliers = new ArrayList<Supplier>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Supplier supplier = new Supplier();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            supplier.setCode(currentCell.getStringCellValue());
                            break;
                        case 1:
                            supplier.setName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            supplier.setEmail(currentCell.getStringCellValue());
                            break;
                        case 3:
                            supplier.setPhone(currentCell.getStringCellValue());
                            break;
                        case 4:
                            supplier.setAddress(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                suppliers.add(supplier);
            }
            workbook.close();
            return suppliers;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream supplierToExcel(List<Supplier> suppliers) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }
            int rowIdx = 1;
            for (Supplier supplier : suppliers) {
                Date dateCreate = new Date(supplier.getCreatedAt().getTime());
                Date modifyDate = new Date(supplier.getUpdateAt().getTime());
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(supplier.getCode());
                row.createCell(1).setCellValue(supplier.getName());
                row.createCell(2).setCellValue(supplier.getEmail());
                row.createCell(3).setCellValue(supplier.getPhone());
                row.createCell(4).setCellValue(supplier.getAddress());
                row.createCell(5).setCellValue(dateCreate);
                row.createCell(6).setCellValue(modifyDate);
                row.createCell(7).setCellValue(supplier.getIsDelete());
                row.createCell(8).setCellValue(supplier.getStatusTransaction() ? "Đang giao dịch" : "Ngừng giao dịch");
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
