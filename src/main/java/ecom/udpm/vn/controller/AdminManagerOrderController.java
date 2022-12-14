package ecom.udpm.vn.controller;


import ecom.udpm.vn.dto.response.OrderPurchaseItem;
import ecom.udpm.vn.dto.response.product.OrderReturnItemResponse;
import ecom.udpm.vn.dto.response.product.OrderReturnResponse;
import ecom.udpm.vn.entity.OrderPurchase;
import ecom.udpm.vn.service.AdminManagerOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/searchAllOrderByStatus/{status_id}")
    public List<OrderPurchase> searchOrderByStatus(@PathVariable Integer status_id, @RequestParam String query) {
        return adminManagerOrderService.searchOrdersByStatus(query, status_id);
    }

    @GetMapping("/searchAllOrder")
    public List<OrderPurchase> searchOrderAll(@RequestParam String query) {
        return adminManagerOrderService.searchOrdersAll(query);
    }

    @GetMapping("/searchOrdersReturn")
    public List<OrderReturnResponse> searchOrdersReturn(@RequestParam String query) {
        return adminManagerOrderService.searchOrdersReturn(query);
    }

    @GetMapping("/showItem/{idOrder}")
    public List<OrderPurchaseItem> showOrderItemByIdOrder(@PathVariable Long idOrder) {
        return adminManagerOrderService.showOrderItemByIdOrder(idOrder);
    }

    @PutMapping("/update-multiple/{status}")
    public void updateMultipleOrder(@PathVariable Integer status, @RequestBody List<Long> ids, @RequestParam String action_by) {
        this.adminManagerOrderService.updateMultiOrderCustomer(ids, status, action_by);
    }


    @PutMapping("/update-one/{status}")
    public void updateOrderCustomer(@PathVariable(value = "status") Integer status, @RequestBody Long ids) {
        this.adminManagerOrderService.updateOrderCustomer(ids, status);
    }
    @GetMapping("/findAll/orderReturn")
    public List<OrderReturnResponse> listReturn() {
        return adminManagerOrderService.showOrdeReturn();
    }
    @GetMapping("/showItem/orderReturn/{idOrder}")
    public List<OrderReturnItemResponse> showOrderReturnItemByIdOrder(@PathVariable Long idOrder){
        return adminManagerOrderService.showOrderReturnItemByIdOrder(idOrder);
    }
    @PutMapping("/update-return-status/{status}")
    public void updateOrderReturn(@PathVariable(value = "status") Integer status, @RequestParam Long idOrderReturn) {
        this.adminManagerOrderService.updateOrderReturn(idOrderReturn, status);
    }
}
