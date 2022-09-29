package ecom.udpm.vn.controller;

import ecom.udpm.vn.models.Supplier;
import ecom.udpm.vn.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apiSupplier/")
public class SupplierController {

    @Autowired
    SupplierService supplierService;
    @GetMapping("/getAll")
    public List<Supplier> getAll(){
        return supplierService.getAll();
    }

}
