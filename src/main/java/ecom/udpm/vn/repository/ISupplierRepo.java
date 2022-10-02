package ecom.udpm.vn.repository;


import ecom.udpm.vn.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ISupplierRepo extends JpaRepository<Supplier,Integer> {
    Optional<Supplier> findByPhone(String phone);
    Optional<Supplier> findByEmail(String email);
    Optional<Supplier> findByCode(String code);

    @Query(value = "select s from Supplier s where s.isDelete = false")
    List<Supplier> findAllByIsDelete();

    @Modifying
    @Transactional
    @Query(value = "update Supplier u set u.isDelete = true where u.id in(:id)")
    void softDeleteAllIds(List<Integer> id);

    @Modifying
    @Transactional
    @Query(value = "update Supplier u set u.statusTransaction = false where u.id in(:listId)")
    void updateStatusFalseTransaction(List<Integer> listId);

    @Modifying
    @Transactional
    @Query(value = "update Supplier u set u.statusTransaction = true where u.id in(:listId)")
    void updateStatusTrueTransaction(List<Integer> listId);

}
