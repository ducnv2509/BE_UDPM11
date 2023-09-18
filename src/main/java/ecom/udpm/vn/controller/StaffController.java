package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.request.DTOEmployeeRequest;
import ecom.udpm.vn.dto.request.DTOUpdateAccountStaffRequest;
import ecom.udpm.vn.entity.Employee;
import ecom.udpm.vn.entity.Staff;
import ecom.udpm.vn.exception.ErrorMessage;
import ecom.udpm.vn.exception.StaffException;
import ecom.udpm.vn.helper.Excel.ExcelStaff;
import ecom.udpm.vn.helper.Excel.ExcelSupplier;
import ecom.udpm.vn.service.IStaffService;
import ecom.udpm.vn.service.SendMailService;
import ecom.udpm.vn.service.impl.StaffService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.ProtocolException;

import java.net.URL;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Log4j2
@CrossOrigin("*")
@RequestMapping("api/staffs")
@AllArgsConstructor
public class StaffController {
    private final IStaffService staffService;
    private final StaffService getStaffServiceV2;
    private final SendMailService sendMailService;
    private final StaffService staffServiceV2;

    @GetMapping
    public Page<Staff> getPagination(@RequestParam(value = "pageNumber", required = true) int pageNumber, @RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "sortDir", required = false) String sortDir) {
        return this.staffService.findAll(pageNumber, pageSize, sortBy, sortDir);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/findAll")
    public List<Employee> list() {
        return this.staffService.findAll();
    }
    @PutMapping("/status/{roleId}/{status}/{id}")
    public void update(@PathVariable Integer roleId, @PathVariable Boolean status, @PathVariable Integer id) {
        DTOUpdateAccountStaffRequest request = new DTOUpdateAccountStaffRequest( roleId, status,id);
        this.getStaffServiceV2.updateAccountStaff(request);
    }
//    @PostMapping
//    public Staff create(@RequestBody @Valid Staff request, BindingResult bindingResult) {
//        try {
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String strDate = dateFormat.format(request.getDob());
////            sendMailService.sendMail(request.getName(), request.getPhone(), request.getAddress(), request.getRoleId() == 1 ? "Quản lý" : "Nhân viên", request.getUsername(), strDate, request.getEmail());
//        } catch (Exception e) {
//            throw new StaffException("Server email error");
//        }
//        return this.staffService.create(request, bindingResult);
//    }

    @PreAuthorize("hasAnyAuthority('admin')")
	@PostMapping
	public ResponseEntity<Object> createAccount(@Valid @RequestBody DTOEmployeeRequest accountDTO) {
		return ResponseEntity.ok(staffServiceV2.createAccountEmployee(accountDTO));
	}

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(this.getStaffServiceV2.findEmployeeById(id));
    }

    // với admin chỉ xem được tt của nhân viên và thay đổi được trạng thái (xoá, active)


    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("/delete")
    public void softDeleteAllIds(@RequestBody List<Long> id) {
        this.staffService.softDeleteAllIds(id);
    }

    @PutMapping("/updateStatus/{status}")
    public void updateStatus(@PathVariable(value = "status") String status, @RequestBody List<Long> ids) {
        if (status.equals("true")) {
            this.staffService.updateStatusTrueAccount(ids);
        } else this.staffService.updateStatusFalseAccount(ids);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("INNNNN");
        String message = "";
        if (ExcelStaff.hasExcelFormat(file)) {
            System.out.println("INNNNN - 1");
            try {
                this.staffService.save(file);
                System.out.println("INNNNN - 3");
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.ok(message);
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ErrorMessage.builder().code("ERROR").message(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder().code("ALREADY_EXIST").message(message));
    }
//    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "staff.xlsx";
        InputStreamResource file = new InputStreamResource(this.staffService.loadExcel());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
