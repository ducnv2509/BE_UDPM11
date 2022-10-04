package ecom.udpm.vn.helper.Excel;

import ecom.udpm.vn.entity.Staff;
import ecom.udpm.vn.entity.Supplier;

import java.io.InputStream;
import java.util.List;

public class ExcelStaff {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = {"Code", "Name", "Email", "Phone", "Address", "Created date", "Modify date", "Is delete", "Status transaction"};


    public static List<Staff> excelToStaffs(InputStream is) {
        return null;
    }
}
