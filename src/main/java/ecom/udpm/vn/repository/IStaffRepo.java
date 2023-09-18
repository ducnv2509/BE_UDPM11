package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.Employee;
import ecom.udpm.vn.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IStaffRepo extends JpaRepository<Staff, Long> {
    Optional<Staff> findByPhone(String phone);

    Optional<Staff> findByEmail(String email);

    Optional<Staff> findByCode(String code);

    Optional<Staff> findByUsername(String username);



    @Query(value = "select s.address, s.email, s.fullName, s.phone, s.code, s.gender, a.createAt, a.isDelete, a.updateAt, a.username from Employee s join Account a on a.id = s.account.id")
    List<Employee> findAllByIsDelete();

    @Modifying
    @Transactional
    @Query(value = "update Staff s set s.statusAccount =:status, s.roleId =:idRole, s.updateAt=:date where s.id =:id")
    void updateStaffById(Boolean status, Long idRole, Long id, Date date);

    @Modifying
    @Transactional
    @Query(value = "update Staff s set s.isDelete = true where s.id in(:id)")
    void softDeleteAllIds(List<Long> id);


    @Modifying
    @Transactional
    @Query(value = "update Staff s set s.statusAccount = false where s.id in(:listId)")
    void updateStatusFalseAccount(List<Long> listId);

    @Modifying
    @Transactional
    @Query(value = "update Staff s set s.statusAccount = true where s.id in(:listId)")
    void updateStatusTrueAccount(List<Long> listId);

}
