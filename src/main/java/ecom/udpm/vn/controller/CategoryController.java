package ecom.udpm.vn.controller;

import ecom.udpm.vn.entity.Category;
import ecom.udpm.vn.service.ICategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Log4j2
@CrossOrigin("*")
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {
    public final ICategoryService iCategoryService;

    @GetMapping("/findall")
    public List<Category> getdAll(@RequestParam(value = "valueInput", required = false) String valueInput){
        return iCategoryService.getAll(valueInput);
    }
}
