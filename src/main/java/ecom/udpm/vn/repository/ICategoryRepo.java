package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ICategoryRepo extends JpaRepository<Category,Long> {
    @Transactional
    @Query(value = "call get_categoriesByIdorName(?1)", nativeQuery = true)
    List<Category> getAllByName(String valueInput);
}
