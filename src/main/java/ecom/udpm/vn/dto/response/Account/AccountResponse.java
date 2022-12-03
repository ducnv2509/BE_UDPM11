package ecom.udpm.vn.dto.response.Account;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
public class AccountResponse {
	private Integer id;
	private String username;
	private java.sql.Timestamp createAt;
	private java.sql.Timestamp updateAt;
	private boolean isDelete;
	private List<Integer> roleIds;
	private List<String> authorities;
	private String fullName;
	private String image;
	private String email;
	private String phone;
	private String address;
//	private Long accountId;
}
