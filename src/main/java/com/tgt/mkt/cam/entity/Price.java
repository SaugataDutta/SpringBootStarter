package com.tgt.mkt.cam.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price implements Serializable {
    private Double retailPrice;
    private Double listPrice;
    private Double offerPrice;
}
