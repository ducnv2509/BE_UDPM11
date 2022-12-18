package ecom.udpm.vn.service;

import ecom.udpm.vn.entity.Category;
import ecom.udpm.vn.entity.Supplier;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ICategoryService {
    List<Category> getAll(String valueInput);
    List<Category> findALl();

    Category addCategories(Category category,  BindingResult bindingResult);

    Supplier create(Supplier t, BindingResult bindingResult);

    void save(MultipartFile file);

    ByteArrayInputStream loadExcel();

    Category findById(Integer id);

    Category update(Category category,BindingResult bindingResult);
}
