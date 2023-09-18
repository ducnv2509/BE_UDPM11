package ecom.udpm.vn.helper.Excel;

import ecom.udpm.vn.entity.Employee;
import ecom.udpm.vn.entity.Staff;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelStaff {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = {"Địa chỉ", "Email", "Họ Tên", "Số điện thoại", "Mã Nhân viên", "Giới tính"};
    static String SHEET = "Staff";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Staff> excelToStaffs(InputStream is) {
        System.out.println("ExcelStaff - 1");
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Staff> staffs = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Staff staff = new Staff();
                int cellIdx = 0;
                System.out.println("ExcelStaff - 2");
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
//                    System.out.println("ExcelStaff - 3");
                    switch (cellIdx) {
                        case 0:
                            staff.setCode(currentCell.getStringCellValue());
                            break;
                        case 1:
                            staff.setName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            staff.setEmail(currentCell.getStringCellValue());
                            break;
                        case 3:
                            staff.setPhone(currentCell.getStringCellValue());
                            break;
                        case 4:
                            staff.setAddress(currentCell.getStringCellValue());
                            break;
                        case 5:
                            staff.setUsername(currentCell.getStringCellValue());
                            break;
                        case 6:
                            staff.setDob(currentCell.getDateCellValue());
                            System.out.println("THis is BUG " + currentCell.getDateCellValue());
                            break;
                        case 7:
                            staff.setGender(currentCell.getBooleanCellValue());
                            break;
                        case 8:
                            staff.setRoleId((long) BigDecimal.valueOf((double) currentCell.getNumericCellValue()).intValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
//                    System.out.println("ExcelStaff - 4");

                }
                System.out.println("ExcelStaff - 5");
                staffs.add(staff);
                System.out.println("ExcelStaff - 6");
            }
            System.out.println("ExcelStaff - 7");
            workbook.close();
            return staffs;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }



    public static ByteArrayInputStream staffToExcel(List<Employee> staffs) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }
            int rowIdx = 1;
            for (Employee staff : staffs) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(staff.getAddress());
                row.createCell(1).setCellValue(staff.getEmail());
                row.createCell(2).setCellValue(staff.getFullName());
                row.createCell(3).setCellValue(staff.getPhone());
                row.createCell(4).setCellValue(staff.getCode());
                row.createCell(5).setCellValue(staff.isGender() ? "Nam" : "Nữ");
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
