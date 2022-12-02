package ecom.udpm.vn.dto.response.revenue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class RevenueFilterRequest  {

    private String timeType = "day";


    private String sortBy;

    @ApiModelProperty(value = "Sắp xếp tăng dần hoặc giảm dần", example = "asc")
    private String sortDirection;

    private String filterBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

    private int limit;

    private int page;

    public int getLimit() {
        if (limit <= 0 || limit > 250)
            return 250;
        else
            return this.limit;
    }

    public int getPage() {
        if (page <= 0)
            return 1;
        else
            return this.page;
    }
}
