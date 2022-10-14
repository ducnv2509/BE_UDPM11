package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.DetailsReturnImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetailImportReturnRepo extends JpaRepository<DetailsReturnImport, Integer> {
}
