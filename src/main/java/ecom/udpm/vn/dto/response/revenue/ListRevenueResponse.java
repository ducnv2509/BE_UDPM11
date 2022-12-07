package ecom.udpm.vn.dto.response.revenue;

import ecom.udpm.vn.dto.model.Metadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListRevenueResponse  {
    List<RevenueResponse> responses;
    private Integer totalOrderCount;
    private BigDecimal totalOrderAmount;
    private BigDecimal totalDiscount;
    private BigDecimal totalReturnOrderAmount;
    private BigDecimal totalRealRevenue;
    private BigDecimal totalShippingFee;
    private BigDecimal totalNetRevenue;
    private Metadata metadata;

    public ListRevenueResponse(List<RevenueResponse> responses, Metadata metadata) {
        this.setResponses(responses);
        this.setMetadata(metadata);
    }
}
