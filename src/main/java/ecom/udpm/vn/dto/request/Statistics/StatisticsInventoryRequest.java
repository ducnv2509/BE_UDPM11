package ecom.udpm.vn.dto.request.Statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsInventoryRequest {
    private Integer  inventoryId;
    private Timestamp startDate;
    private Timestamp endDate;
    private String keySearch;
    private String sortBy;
    private boolean sortDir;
    private  Integer page;
    private  Integer size;
    private  Integer type;
}
