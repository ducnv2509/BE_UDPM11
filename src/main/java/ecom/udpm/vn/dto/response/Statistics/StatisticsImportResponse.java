package ecom.udpm.vn.dto.response.Statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsImportResponse {
    private Integer inventoryId;
    private Integer supplierId;
    private Integer accountId;
    private Integer importId;
    private String importCode;
    private Integer detailsImportId;
    private Integer productVariantId;
    private BigDecimal importPrice;
    private BigDecimal avgPrice;

    private Integer importNumber;
    private BigDecimal totalPrice;
    private String deliveryDate;
    private java.sql.Timestamp createAt;
    private String code;
    private String name;
    private Integer productId;
    private String productName;
    private Integer returnNumber;
    private Integer receiveNumber;


}
