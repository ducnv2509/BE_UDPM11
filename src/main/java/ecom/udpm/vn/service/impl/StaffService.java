package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.entity.Staff;
import ecom.udpm.vn.exception.AlreadyExistsException;
import ecom.udpm.vn.exception.StaffException;
import ecom.udpm.vn.helper.Excel.ExcelStaff;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        this.iStaffRepo.findByUsername(s.getUsername()).ifPresent(e -> {
            throw new StaffException("Username đã tồn tại");
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
            System.out.println("Service - 3");
            List<Staff> staff = ExcelStaff.excelToStaffs(file.getInputStream());
            for (Staff s : staff
            ) {
                s.setUpdateAt(Timestamp.from(Instant.now()));
                s.setUpdateAt(Timestamp.from(Instant.now()));
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String strDate = dateFormat.format(s.getDob());
//                System.out.println("Converted date: " + strDate);
//                Date date1 = new Date(strDate);
                String password = "abc@123";
                String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
                s.setPassword(hash);
                System.out.println("before " + s.getDob());
                s.setDob(s.getDob());
//                System.out.println("Converted String: " + strDate);
            }

            System.out.println(staff);
            System.out.println("Service - 4");
            this.iStaffRepo.saveAll(staff);
            System.out.println("Service - 5");

        } catch (Exception e) {
            throw new StaffException("fail to store excel data: " + e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream loadExcel() {
        List<Staff> tutorials = this.iStaffRepo.findAllByIsDelete();
        ByteArrayInputStream in = ExcelStaff.staffToExcel(tutorials);
        return in;
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
    public void updateStaffById(Boolean status, Long idRole, Long id, Date date) {
        this.iStaffRepo.updateStaffById(status, idRole, id, new Date());
    }
}
