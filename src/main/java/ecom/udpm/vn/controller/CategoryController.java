package ecom.udpm.vn.controller;

import ecom.udpm.vn.entity.Category;
import ecom.udpm.vn.service.ICategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import ecom.udpm.vn.entity.Supplier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import java.util.List;
import javax.validation.Valid;
@RestController
@Log4j2
@CrossOrigin("*")
@RequestMapping("/api/categories")
@AllArgsConstructor
@PreAuthorize("hasAnyAuthority('admin')")
public class CategoryController {
    public final ICategoryService iCategoryService;

    @GetMapping("/findall")
    public List<Category> getdAll(@RequestParam(value = "valueInput", required = false) String valueInput){
        return iCategoryService.getAll(valueInput);
    }
    @GetMapping("/getAll")
    public List<Category> getAllCategories(){
        return iCategoryService.findALl();
    }

    @PostMapping
    public Category create(@RequestBody @Valid Category request, BindingResult bindingResult) {
        return iCategoryService.addCategories(request, bindingResult);
    }

    @GetMapping("{id}")
    public Category findById(@PathVariable(value = "id") Integer id) {
        return iCategoryService.findById(id);
    }


    @PutMapping
    public Category update(@RequestBody @Valid Category entity, BindingResult bindingResult) {
        return iCategoryService.update(entity, bindingResult);
    }
}
