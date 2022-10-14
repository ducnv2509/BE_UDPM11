package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusRepo extends JpaRepository<Status, Integer> {
    Status findByCode(String import01);


}
