package ecom.udpm.vn.repository;


import ecom.udpm.vn.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Employee, Long> {

}
