package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.request.OrderPurchaseCreateRequest;
import ecom.udpm.vn.service.IOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@Log4j2
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/orderPurchase")
public class OrderPurchaseController {
    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createCart(@RequestBody OrderPurchaseCreateRequest orderPurchase) {
        System.out.println("inController");
        return ResponseEntity.ok(orderService.createOrder(orderPurchase.getId_account(), orderPurchase.getAddress(),  orderPurchase.getNote()));
    }

    @GetMapping("/pdf/{id_account}/{id_order}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Integer id_account, @PathVariable Integer id_order) throws FileNotFoundException, JRException {
        System.out.println("inController");
        return orderService.createPdf(id_account, id_order);
    }
}
