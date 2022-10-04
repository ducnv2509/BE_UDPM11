package ecom.udpm.vn.dto.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Getter
@Setter
public class CategoryResponse {

    private Integer id;
    private String name;
    private String description;
}
