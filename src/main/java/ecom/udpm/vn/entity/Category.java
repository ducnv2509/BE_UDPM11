package ecom.udpm.vn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "name", nullable = false, length = 500)
    @NotEmpty(message = "Danh mục không thể để trống")
    private String name;

    @Lob
    @NotEmpty(message = "Mô tả không thể để trống")
    @Column(name = "description", nullable = false)
    private String description;



}