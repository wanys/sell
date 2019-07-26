package com.sell.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductForm {
    private String productId;

    private String productName;
    //单价
    private BigDecimal productPrice;
    //库存
    private Integer productStock;
    //描述
    private String productDescription;
    //小图
    private String productIcon;
    /*状态  1 正常   2下架*/
/*    private Integer productStatus;*/
    /*类目编号*/
    private Integer categoryType;


}
