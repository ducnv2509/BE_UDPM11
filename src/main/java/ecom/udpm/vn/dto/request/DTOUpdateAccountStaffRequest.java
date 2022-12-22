package ecom.udpm.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DTOUpdateAccountStaffRequest {
    private Integer role_id;
    private Boolean delete;
    private Integer id;
}
