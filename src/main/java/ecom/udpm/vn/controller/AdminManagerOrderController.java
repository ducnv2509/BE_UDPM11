package ecom.udpm.vn.controller;


import ecom.udpm.vn.entity.OrderPurchase;
import ecom.udpm.vn.entity.Supplier;
import ecom.udpm.vn.service.AdminManagerOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@CrossOrigin("*")
@RequestMapping("/api/manager-oder")
@AllArgsConstructor
@RestController
public class AdminManagerOrderController {

    @Autowired
    AdminManagerOrderService adminManagerOrderService;


    @GetMapping("/findAll")
    public List<OrderPurchase> list() {
        return adminManagerOrderService.showOrderCustomer();
    }

    @GetMapping("/findAll/status/{status_id}")
    public List<OrderPurchase> findAllByStatus(@PathVariable Integer status_id) {
        return adminManagerOrderService.showOrderCustomerByStatus(status_id);
    }
    @PutMapping("/update-multiple/{status}")
    public void updateMultipleOrder(@PathVariable Integer status, @RequestBody List<Long> ids) {
        this.adminManagerOrderService.updateMultiOrderCustomer(ids, status);
    }


    @PutMapping("/update-one/{status}")
    public void updateOrderCustomer(@PathVariable(value = "status") Integer status, @RequestBody Long ids) {
        this.adminManagerOrderService.updateOrderCustomer(ids, status);
    }

}
