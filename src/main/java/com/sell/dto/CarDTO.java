package com.sell.dto;

import lombok.Data;

@Data
public class CarDTO {
    /*商品ID*/
    private  String productId;
//    数量
    private Integer productQuantity;

    public CarDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
