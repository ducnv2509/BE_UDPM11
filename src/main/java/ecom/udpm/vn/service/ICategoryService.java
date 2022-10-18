package ecom.udpm.vn.service;

import ecom.udpm.vn.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAll(String valueInput);

}
