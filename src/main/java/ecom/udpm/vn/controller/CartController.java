package ecom.udpm.vn.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import ecom.udpm.vn.dto.request.ProductAddRequest;
import ecom.udpm.vn.entity.Cart;
import ecom.udpm.vn.entity.CartItems;
import ecom.udpm.vn.service.ICartService;
import ecom.udpm.vn.service.IProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@CrossOrigin("*")
@RequestMapping("/api/cart")
@AllArgsConstructor
@RestController
public class CartController {
    private final ICartService cartService;

    @PostMapping("/{id}")
    public ResponseEntity<?> createCart(@RequestBody CartItems cartItems, @PathVariable Long id) {
        System.out.println("inController");
        return ResponseEntity.ok(cartService.addItems(id, cartItems.getId_product().longValue(), cartItems.getQuantity()));
    }
}
