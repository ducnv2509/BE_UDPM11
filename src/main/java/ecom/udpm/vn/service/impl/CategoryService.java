package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.entity.Category;
import ecom.udpm.vn.entity.Supplier;
import ecom.udpm.vn.exception.AlreadyExistsException;
import ecom.udpm.vn.exception.StaffException;
import ecom.udpm.vn.helper.Utils;
import ecom.udpm.vn.repository.ICategoryRepo;
import ecom.udpm.vn.repository.ICategoryV2;
import ecom.udpm.vn.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryV2 iCategoryRepo;
    private final ICategoryRepo categoryRepo;
    private final Utils utils;


    @Override
    public List<Category> getAll(String valueInput) {
        return categoryRepo.getAllByName(valueInput);
    }

    @Override
    public List<Category> findALl() {
        return iCategoryRepo.findAll();
    }

    @Override
    public Category addCategories(Category category , BindingResult bindingResult) {
        iCategoryRepo.findByName(category.getName()).ifPresent(e -> {
            throw new StaffException("Tên danh mục đã tồn tại");
        });
        if (bindingResult.hasErrors()) {
            throw utils.invalidInputException(bindingResult);
        }
        return iCategoryRepo.save(category);
    }

    @Override
    public Supplier create(Supplier t, BindingResult bindingResult) {
        return null;
    }

    @Override
    public void save(MultipartFile file) {

    }

    @Override
    public ByteArrayInputStream loadExcel() {
        return null;
    }

    @Override
    public Category findById(Integer id) {
        return iCategoryRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found: " + id));

    }

    @Override
    public Category update(Category category, BindingResult bindingResult) {
        var t = iCategoryRepo.findById(category.getId()).orElseThrow(() -> new IllegalArgumentException(("id not found: " + category.getId())));
        if (!t.getName().equals(category.getName())) {
            iCategoryRepo.findByName(category.getName()).ifPresent(e -> {
                throw new AlreadyExistsException("Danh mục đã tồn tại");
            });
        }
        BindingResult result = utils.getListResult(bindingResult, category);
        if (result.hasErrors()) {
            throw utils.invalidInputException(result);
        } else {
            return iCategoryRepo.save(category);
        }
    }
}
