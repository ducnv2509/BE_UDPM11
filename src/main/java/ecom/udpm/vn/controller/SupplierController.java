package ecom.udpm.vn.controller;

import ecom.udpm.vn.entity.Supplier;
import ecom.udpm.vn.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/apiSupplier/")
public class SupplierController {

    private  final SupplierService supplierService;
    @GetMapping("/getAll")
    public List<Supplier> getAll(){
        return supplierService.getAll();
    }

}
