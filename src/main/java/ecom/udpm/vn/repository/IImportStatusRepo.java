package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.ImportsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImportStatusRepo extends JpaRepository<ImportsStatus, Integer> {
    ImportsStatus findByImportIdAndStatusId(Integer importId, Integer statusId);
}
