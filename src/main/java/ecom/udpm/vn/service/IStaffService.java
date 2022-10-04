package ecom.udpm.vn.service;

import ecom.udpm.vn.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

public interface IStaffService {

    Page<Staff> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<Staff> findAll();

    Staff create(Staff s, BindingResult bindingResult);

    void save(MultipartFile file);

    ByteArrayInputStream loadExcel();

    Staff findById(Long id);

    Staff update(Staff s, BindingResult bindingResult);


    void softDeleteAllIds(List<Long> listId);

    void updateStatusFalseAccount(List<Long> listId);

    void updateStatusTrueAccount(List<Long> listId);

    void updateStaffById(Boolean status, Long idRole, Long id, Date date);



}
