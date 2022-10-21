package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.request.ProductAddRequest;
import ecom.udpm.vn.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping()
    public ResponseEntity create(@RequestBody @Valid ProductAddRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(productService.save(request, bindingResult));
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity getProductByOption(@RequestParam String op1, @RequestParam String op2, @RequestParam String op3, @PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductByOption(op1, op2, op3, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOptionProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getOptionProduct(id));
    }

}
