//package ecom.udpm.vn.controller;
//
//import ecom.udpm.vn.models.Staff;
//import ecom.udpm.vn.models.Supplier;
//import ecom.udpm.vn.service.StaffService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/apiStaff/")
//public class StaffController {
//    @Autowired
//    StaffService staffService;
//    @GetMapping("/getAll")
//    public List<Staff> getAll(){
//        return this.staffService.getAll();
//    }
//
//    @GetMapping("/getById/{id}")
//    public Staff getById(@PathVariable Long id){
//        return this.staffService.getById(id);
//    }
//
//    @PostMapping("/create")
//    public Staff create(@RequestBody Staff staff){
//        return this.staffService.create(staff);
//    }
//
//    @PutMapping("/update")
//    public Staff update(@RequestBody Staff staff){
//        return this.staffService.update(staff);
//    }
//
//    @DeleteMapping("/delete")
//    public void delete(@RequestBody Long id){
//        this.staffService.delete(id);
//    }
//}
