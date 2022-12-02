package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.response.revenue.ListRevenueResponse;
import ecom.udpm.vn.dto.response.revenue.RevenueFilterRequest;
import ecom.udpm.vn.dto.response.revenue.RevenueResponse;
import ecom.udpm.vn.service.impl.RevenueService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/tracking")
@AllArgsConstructor
public class TrackingController {
    private final RevenueService revenueService;

    @ApiOperation(value = "Xem biểu đồ doanh thu")
    @GetMapping("revenue")
    public List<RevenueResponse> getRevenueChart(@Valid RevenueFilterRequest request) {
        return revenueService.getListRevenue(request);
    }
    @ApiOperation(value = "Xem thông số chi tiết doanh thu")
    @GetMapping("details/revenue")
    public ListRevenueResponse getDetailsRevenueChart(@Valid RevenueFilterRequest request) {
        return revenueService.getListRevenueRespone(request);
    }
}
