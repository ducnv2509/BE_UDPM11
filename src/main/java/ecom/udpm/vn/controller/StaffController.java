package ecom.udpm.vn.controller;

import ecom.udpm.vn.entity.Staff;
import ecom.udpm.vn.service.IStaffService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Log4j2
@CrossOrigin("*")
@RequestMapping("api/staffs")
@AllArgsConstructor
public class StaffController {
    private final IStaffService staffService;


    @GetMapping
    public Page<Staff> getPagination(@RequestParam(value = "pageNumber", required = true) int pageNumber, @RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "sortDir", required = false) String sortDir) {
        return this.staffService.findAll(pageNumber, pageSize, sortBy, sortDir);
    }


    @GetMapping("/findAll")
    public List<Staff> list() {
        return this.staffService.findAll();
    }

    @PostMapping
    public Staff create(@RequestBody @Valid Staff request, BindingResult bindingResult) {
        return this.staffService.create(request, bindingResult);
    }


    @GetMapping("{id}")
    public Staff findById(@PathVariable(value = "id") Long id) {
        return this.staffService.findById(id);
    }

    // với admin chỉ xem được tt của nhân viên và thay đổi được trạng thái (xoá, active)

    @PutMapping("{status}/{roleId}/{id}")
    public void update(@PathVariable Boolean status, @PathVariable Long roleId, @PathVariable Long id) {
        this.staffService.updateStaffById(status, roleId, id);
    }

    @PutMapping("/delete")
    public void softDeleteAllIds(@RequestBody List<Long> id) {
        this.staffService.softDeleteAllIds(id);
    }

    @PutMapping("/updateStatus/{status}")
    public void updateStatus(@PathVariable(value = "status") String status, @RequestBody List<Long> ids) {
        if (status.equals("true")) {
            this.staffService.updateStatusTrueAccount(ids);
        } else this.staffService.updateStatusFalseAccount(ids);
    }


}
