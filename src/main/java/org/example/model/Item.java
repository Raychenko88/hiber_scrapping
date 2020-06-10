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

    private String itemId;
    private String name;
    private String url;
    private String imageUrl;
    private BigDecimal price;
    private BigDecimal initialPrice;
    private String availability;
}
