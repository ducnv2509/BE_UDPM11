package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.ReturnImport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReturnImportRepo extends JpaRepository<ReturnImport, Integer> {
    List<ReturnImport> findByImportId(Integer id);
}
