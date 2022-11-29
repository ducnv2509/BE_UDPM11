package ecom.udpm.vn.dto.response.revenue;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class RevenueResponse {
    private String date;
    private BigDecimal totalOrderAmount;
    private Integer orderCount;
    private BigDecimal discountAmount;
    private BigDecimal returnOrderAmount;
    private BigDecimal realRevenue;
    private BigDecimal shippingAmount;
    private BigDecimal netRevenue;
    private BigDecimal lastPeriodNetRevenue;
    private String lastPeriodDate;
}
