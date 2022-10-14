package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IImportRepo extends JpaRepository<Import, Long> {
    Optional<Import> findByCode(String code);

    Optional<Import> findById(Integer id);
}
