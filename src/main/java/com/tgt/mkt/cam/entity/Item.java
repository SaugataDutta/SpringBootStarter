package com.tgt.mkt.cam.entity;


import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name="item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TypeDef(name = "PriceJsonType", typeClass = JsonUserType.class)
public class Item {
    @Column(name = "product_id")
    @Id
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name="product_price")
    @Type(type = "PriceJsonType")
    private Price price;
}
