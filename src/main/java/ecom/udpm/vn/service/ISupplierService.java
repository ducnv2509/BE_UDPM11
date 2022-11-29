package ecom.udpm.vn.service;

import ecom.udpm.vn.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ISupplierService {

    Page<Supplier> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<Supplier> findAll();

    Supplier create(Supplier t, BindingResult bindingResult);

    void save(MultipartFile file);

    ByteArrayInputStream loadExcel();

    Supplier findById(int id);

    Supplier update(Supplier t,BindingResult bindingResult);



    void softDeleteAllIds(List<Integer> listId);

    void updateStatusFalseTransaction(List<Integer> listId);

    void updateStatusTrueTransaction(List<Integer> listId);
}
