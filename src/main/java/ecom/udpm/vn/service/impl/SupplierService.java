package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.entity.Supplier;
import ecom.udpm.vn.exception.AlreadyExistsException;
import ecom.udpm.vn.helper.Excel.ExcelSupplier;
import ecom.udpm.vn.helper.Utils;
import ecom.udpm.vn.repository.ISupplierRepo;
import ecom.udpm.vn.service.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService {

    private final ISupplierRepo supplierRepo;

    private final Utils utils;

    @Override
    public Page<Supplier> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        if (sortDir != null) {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            return supplierRepo.findAll(PageRequest.of(pageNumber - 1, pageSize, sort));
        }
        return supplierRepo.findAll(PageRequest.of(pageNumber - 1, pageSize));
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepo.findAllByIsDelete();
    }

    @Override
    public Supplier create(Supplier request, BindingResult bindingResult) {
        supplierRepo.findByPhone(request.getPhone()).ifPresent(e -> {
            throw new AlreadyExistsException("Số điện thoại đã tồn tại");
        });
        supplierRepo.findByEmail(request.getEmail()).ifPresent(e -> {
            throw new AlreadyExistsException("Email đã tồn tại");
        });
        supplierRepo.findByCode(request.getCode()).ifPresent(e -> {
            throw new AlreadyExistsException("Code đã tồn tại");
        });
        if (bindingResult.hasErrors()) {
            throw utils.invalidInputException(bindingResult);
        }
        return supplierRepo.save(request);

    }

    @Override
    public void save(MultipartFile file) {
        try {
            List<Supplier> suppliers = ExcelSupplier.excelToSuppliers(file.getInputStream());
            supplierRepo.saveAll(suppliers);
        } catch (Exception e) {
            throw new AlreadyExistsException("fail to store excel data");
        }
    }

    @Override
    public ByteArrayInputStream loadExcel() {
        List<Supplier> tutorials = supplierRepo.findAllByIsDelete();
        ByteArrayInputStream in = ExcelSupplier.supplierToExcel(tutorials);
        return in;
    }

    @Override
    public Supplier findById(int id) {
        return supplierRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found: " + id));
    }

    @Override
    public Supplier update(Supplier request, BindingResult bindingResult) {
        var t = supplierRepo.findById(request.getId()).orElseThrow(() -> new IllegalArgumentException(("id not found: " + request.getId())));
        if (!t.getPhone().equals(request.getPhone())) {
            supplierRepo.findByPhone(request.getPhone()).ifPresent(e -> {
                throw new AlreadyExistsException("Số điện thoại đã tồn tại");
            });
        } else if (!t.getEmail().equals(request.getEmail())) {
            supplierRepo.findByEmail(request.getEmail()).ifPresent(e -> {
                throw new AlreadyExistsException("email đã tồn tại");
            });
        }
        BindingResult result = utils.getListResult(bindingResult, request);
        if (result.hasErrors()) {
            throw utils.invalidInputException(result);
        } else {
            request.setCreatedAt(t.getCreatedAt());
            return supplierRepo.save(request);
        }
    }

    @Override
    public void softDeleteAllIds(List<Integer> listId) {
        listId.forEach(id -> supplierRepo.findById(id).orElseThrow(() -> new IllegalArgumentException(("id not found: " + id))));
        supplierRepo.softDeleteAllIds(listId);
    }

    @Override
    public void updateStatusFalseTransaction(List<Integer> listId) {
        listId.forEach(id -> supplierRepo.findById(id).orElseThrow(() -> new IllegalArgumentException(("id not found: " + id))));
        supplierRepo.updateStatusFalseTransaction(listId);
    }

    @Override
    public void updateStatusTrueTransaction(List<Integer> listId) {
        listId.forEach(id -> supplierRepo.findById(id).orElseThrow(() -> new IllegalArgumentException(("id not found: " + id))));
        supplierRepo.updateStatusTrueTransaction(listId);
    }
}