package ecom.udpm.vn.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductFilterResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long statusId;
    private Long supplierId;
    private Long accountId;
    private java.sql.Timestamp createAt;
    private java.sql.Timestamp updateAt;
    private Boolean isDelete;
    private Long numberOfVariant;
    private Long total;

}
