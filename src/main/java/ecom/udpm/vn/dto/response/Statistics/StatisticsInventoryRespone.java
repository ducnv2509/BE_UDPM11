package ecom.udpm.vn.dto.response.Statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticsInventoryRespone {
    private Integer productVariantId;
    private String productVariantCode;
    private String productVariantName;
    private Integer productId;
    private Integer importNumber;
    private Integer returnNumber;
    private Integer exportNumber;
    private Integer quantity;

}
