package org.example.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "items")
//@MappedSuperclass
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "item_code")
    private String itemCode;
    private String name;
    private String url;
    @Column(name = "image_url")
    private String imageUrl;
    private BigDecimal price;
    @Column(name = "initial_price")
    private BigDecimal initialPrice;
    private String availability;
}
