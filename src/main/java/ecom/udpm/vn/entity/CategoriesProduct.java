package ecom.udpm.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "categories_products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesProduct {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private CategoriesProductId id;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @MapsId("categoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;



}