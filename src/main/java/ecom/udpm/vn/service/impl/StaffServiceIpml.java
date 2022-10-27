//package ecom.udpm.vn.service.impl;
//
//import ecom.udpm.vn.models.Staff;
//import ecom.udpm.vn.repository.StaffRepository;
//import ecom.udpm.vn.service.StaffService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class StaffServiceIpml implements StaffService {
//    @Autowired
//    StaffRepository staffRepository;
//    @Override
//    public List<Staff> getAll() {
//        return staffRepository.findAll();
//    }
//
//    @Override
//    public Staff getById(Long id) {
//        if (staffRepository.findById(id).isPresent()) {
//            return staffRepository.findById(id).get();
//        }
//        return null;
//    }
//
//    @Override
//    public Staff create(Staff staff) {
//        staff.setId(null);
//        boolean flag = false;
//        for (Staff s : getAll()) {
//            if (s.getPhone().equals(staff.getPhone())) {
//                flag = true;
//            }
//            if (s.getEmail().equals(staff.getEmail())) {
//                flag = true;
//            }
//        }
//        if (!flag) {
//            return staffRepository.save(staff);
//        }else {
//            return null;
//        }
//    }
//
//    @Override
//    public Staff update(Staff staff) {
//        return staffRepository.save(staff);
//    }
//
//    @Override
//    public void delete(Long id) {
//        staffRepository.deleteById(id);
//    }
//}
