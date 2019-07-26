package com.sell.dataobject;

import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="order_master")
public class OrderMaster {
    /*订单ID*/
    @Id
    private String orderId;
/*买家名字*/
    private String buyerName;
/*买家手机号*/
    private String buyerPhone;
/*买家地址*/
    private String buyerAddress;
/*买家微信openid*/
    private String buyerOpenid;
/*订单jine*/
    private BigDecimal orderAmount;
/*订单状态  默认为新下单*/
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();
/*支付状态，默认为0 未支付*/
    private Integer payStatus= PayStatusEnum.WAIT.getCode();
/*创建时间*/
    private Date createTime;
/*更新时间*/
    private Date updateTime;

/*
    @Transient//数据库就不在检测这个属性
    private List<OrderDetail> orderDetailsList;
*/

}