package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.response.ImportInvoice.DetailsImportsInvoiceResponse;
import ecom.udpm.vn.dto.response.ImportInvoice.ImportResponse;
import ecom.udpm.vn.entity.DetailsImport;
import ecom.udpm.vn.entity.Import;
import ecom.udpm.vn.service.IDetailsImportService;
import ecom.udpm.vn.service.IImportService;
import ecom.udpm.vn.service.IImportsStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/imports")
@CrossOrigin("*")

public class ImportController {

    private final IImportService importService;
    private final IImportsStatusService importsStatusService;

    private final IDetailsImportService detailsImportService;

    @PostMapping()
    public Import save(@RequestBody Import im) {
        Import anImport = importService.save(im);
        List<DetailsImport> list = detailsImportService.save(im.getDetailsImports(), anImport.getId());
        return anImport;
    }

    @GetMapping
    public List<Import> findAll() {
        return importService.findAll();
    }

    @GetMapping("/findAll")
    public List<ImportResponse> findAllDTO(@RequestParam String searchValue) {
        return importService.findAllImportDTO(searchValue);
    }

    @GetMapping("/getDetails/{code}")
    public DetailsImportsInvoiceResponse getDetails(@PathVariable String code) {
        return importService.getDetailInvoiceByCode(code);
    }

    @PutMapping("/updateStatus")
    public void updateStatus(@RequestParam Integer id, @RequestParam String status, @RequestParam Integer accountId) {
        importService.updateStatusImport(id, status, accountId);
    }

    @PutMapping("/updateStatusReturn")
    public void updateStatusReturn(@RequestParam Integer id, @RequestParam String status, Integer accountId) {
        importService.updateStatusImportReturn(id, status, accountId);
    }

    @GetMapping("/getStatusHistory/{importId}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer importId) {
        return ResponseEntity.ok(importsStatusService.findDetailsImportStatus(importId));
    }

    @GetMapping("/getReturnImport/{code}")
    public ResponseEntity<?> getAllReturnImport(@PathVariable String code) {
        return ResponseEntity.ok(importService.getAllReturnImport(code));
    }

    @GetMapping("/getDetailsReturnImport/{code}")
    public ResponseEntity<?> getDetailsReturn(@PathVariable String code) {
        return ResponseEntity.ok(importService.getDetailsReturnImport(code));
    }

    @GetMapping("/getImportInvoiceBySuppler/{id}")
    public ResponseEntity<?> getInvoiceBySupplier(@PathVariable Integer id) {
        return ResponseEntity.ok(importService.getImportInvoiceBySupplier(id));
    }
}
