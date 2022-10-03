package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.entity.Staff;
import ecom.udpm.vn.entity.Supplier;
import ecom.udpm.vn.exception.AlreadyExistsException;
import ecom.udpm.vn.exception.StaffException;
import ecom.udpm.vn.helper.Excel.ExcelStaff;
import ecom.udpm.vn.helper.Excel.ExcelSupplier;
import ecom.udpm.vn.helper.Utils;
import ecom.udpm.vn.repository.IStaffRepo;
import ecom.udpm.vn.service.IStaffService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService implements IStaffService {
    private final IStaffRepo iStaffRepo;

    private final Utils utils;

    @Override
    public Page<Staff> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        if (sortDir != null) {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            return iStaffRepo.findAll(PageRequest.of(pageNumber - 1, pageSize, sort));
        }
        return iStaffRepo.findAll(PageRequest.of(pageNumber - 1, pageSize));
    }

    @Override
    public List<Staff> findAll() {
        return this.iStaffRepo.findAllByIsDelete();
    }

    @Override
    public Staff create(Staff s, BindingResult bindingResult) {
        this.iStaffRepo.findByPhone(s.getPhone()).ifPresent(e -> {
            throw new StaffException("Số điện thoại đã tồn tại");
        });
        this.iStaffRepo.findByEmail(s.getEmail()).ifPresent(e -> {
            throw new StaffException("Email đã tồn tại");
        });
        this.iStaffRepo.findByCode(s.getCode()).ifPresent(e -> {
            throw new StaffException("Code đã tồn tại");
        });
        if (bindingResult.hasErrors()) {
            throw utils.invalidInputException(bindingResult);
        }
        String password = "abc@123";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        s.setPassword(hash);
        s.setCreatedAt(Timestamp.from(Instant.now()));
        s.setUpdateAt(Timestamp.from(Instant.now()));
        return this.iStaffRepo.save(s);
    }

    @Override
    public void save(MultipartFile file) {
        try {
            List<Staff> staff = ExcelStaff.excelToStaffs(file.getInputStream());
            this.iStaffRepo.saveAll(staff);
        } catch (Exception e) {
            throw new StaffException("fail to store excel data");
        }
    }

    @Override
    public ByteArrayInputStream loadExcel() {
        return null;
    }

    @Override
    public Staff findById(Long id) {
        return this.iStaffRepo.findById(id).orElseThrow(() -> new StaffException("id not found: " + id));
    }

    @Override
    public Staff update(Staff s, BindingResult bindingResult) {
        var t = this.iStaffRepo.findById(s.getId()).orElseThrow(() -> new StaffException(("id not found: " + s.getId())));
        if (!t.getPhone().equals(s.getPhone())) {
            this.iStaffRepo.findByPhone(s.getPhone()).ifPresent(e -> {
                throw new AlreadyExistsException("Số điện thoại đã tồn tại");
            });
        } else if (!t.getEmail().equals(s.getEmail())) {
            this.iStaffRepo.findByEmail(s.getEmail()).ifPresent(e -> {
                throw new AlreadyExistsException("email đã tồn tại");
            });
        }
        BindingResult result = utils.getListResult(bindingResult, s);
        if (result.hasErrors()) {
            throw utils.invalidInputException(result);
        } else {
            s.setCreatedAt(t.getCreatedAt());
            return this.iStaffRepo.save(s);
        }
    }

    @Override
    public void softDeleteAllIds(List<Long> listId) {
        listId.forEach(id -> this.iStaffRepo.findById(id).orElseThrow(() -> new StaffException(("id not found: " + id))));
        this.iStaffRepo.softDeleteAllIds(listId);
    }

    @Override
    public void updateStatusFalseAccount(List<Long> listId) {
        listId.forEach(id -> this.iStaffRepo.findById(id).orElseThrow(() -> new StaffException(("id not found: " + id))));
        this.iStaffRepo.updateStatusFalseAccount(listId);
    }

    @Override
    public void updateStatusTrueAccount(List<Long> listId) {
        listId.forEach(id -> this.iStaffRepo.findById(id).orElseThrow(() -> new StaffException(("id not found: " + id))));
        this.iStaffRepo.updateStatusTrueAccount(listId);
    }

    @Override
    public void updateStaffById( Boolean status, Long idRole, Long id) {
        this.iStaffRepo.updateStaffById(status, idRole, id);
    }
}
