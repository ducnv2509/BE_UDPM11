package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.CategoriesProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryProduct extends JpaRepository<CategoriesProduct,Long> {
}
