package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.entity.Category;
import ecom.udpm.vn.repository.ICategoryRepo;
import ecom.udpm.vn.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepo iCategoryRepo;

    @Override
    public List<Category> getAll(String valueInput) {
        return iCategoryRepo.getAllByName(valueInput);
    }
}
