package ecom.udpm.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductFilter {
    private String key;
    private Integer statusId;
    @NotNull
    private Boolean isDelete;
    private String sortBy;
    private Boolean isDesc;
    @NotNull(message = "page must be not null")
    @Min(value = 1,message = "page must be greater than 1")
    private Integer page;
    @NotNull(message = "size must be not null")
    @Min(value = 1,message = "size must be greater than 1")
    private Integer size;

}
