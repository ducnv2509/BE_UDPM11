package ecom.udpm.vn.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
public class OptionResponse {
    public Integer id;
    public String name;
    public Integer position;
    public Timestamp createdAt;
    public Timestamp modifiedAt;
    public String[] values;
}
