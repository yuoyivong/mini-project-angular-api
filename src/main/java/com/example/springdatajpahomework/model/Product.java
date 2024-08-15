package com.example.springdatajpahomework.model;

import com.example.springdatajpahomework.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "unit_price")
    private Float unitPrice;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Order> orderList;

    public ProductDTO productDTOResponse() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(this.productId);
        productDTO.setProductName(this.productName);
        productDTO.setDescription(this.description);
        productDTO.setUnitPrice(this.unitPrice);

        return productDTO;
    }

}
