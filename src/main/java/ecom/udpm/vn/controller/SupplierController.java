package ecom.udpm.vn.controller;

import ecom.udpm.vn.entity.Supplier;
import ecom.udpm.vn.exception.ErrorMessage;
import ecom.udpm.vn.helper.Excel.ExcelSupplier;
import ecom.udpm.vn.service.ISupplierService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@Log4j2
@CrossOrigin("*")
@RequestMapping("api/suppliers")
@AllArgsConstructor
public class SupplierController {

    private final ISupplierService supplierService;


    @GetMapping
    public Page<Supplier> getPagination(@RequestParam(value = "pageNumber", required = true) int pageNumber,
                                        @RequestParam(value = "pageSize", required = true) int pageSize,
                                        @RequestParam(value = "sortBy", required = false) String sortBy,
                                        @RequestParam(value = "sortDir", required = false) String sortDir) {
        return supplierService.findAll(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/findAll")
    public List<Supplier> list() {
        return supplierService.findAll();
    }

    @PostMapping
    public Supplier create(@RequestBody @Valid Supplier request, BindingResult bindingResult) {
        return supplierService.create(request, bindingResult);
    }

    @GetMapping("{id}")
    public Supplier findById(@PathVariable(value = "id") Integer id) {
        return supplierService.findById(id);
    }


    @PutMapping
    public Supplier update(@RequestBody @Valid Supplier entity, BindingResult bindingResult) {
        return supplierService.update(entity, bindingResult);
    }

    @PutMapping("/delete")
    public void softDeleteAllIds(@RequestBody List<Integer> id) {
        supplierService.softDeleteAllIds(id)
        ;
    }

    @PutMapping("/updateStatus/{status}")
    public void updateStatus(@PathVariable(value = "status") String status, @RequestBody List<Integer> ids) {
        if (status.equals("true")) {
            supplierService.updateStatusTrueTransaction(ids);
        } else supplierService.updateStatusFalseTransaction(ids);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (ExcelSupplier.hasExcelFormat(file)) {
            try {
                supplierService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.ok(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ErrorMessage.builder().code("ERROR").message(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder().code("ALREADY_EXIST").message(message));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "supplier.xlsx";
        InputStreamResource file = new InputStreamResource(supplierService.loadExcel());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}