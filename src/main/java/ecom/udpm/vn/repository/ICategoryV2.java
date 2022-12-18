package ecom.udpm.vn.repository;
import ecom.udpm.vn.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ICategoryV2 extends JpaRepository<Category,Integer>{
    Optional<Category> findByName(String name);
}
