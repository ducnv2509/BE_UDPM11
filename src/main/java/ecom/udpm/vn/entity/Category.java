package ecom.udpm.vn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @Column(name = "name", nullable = false, length = 500)
    @NotNull
    @NotBlank
    private String name;

    @Lob
    @NotNull
    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;



}