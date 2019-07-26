package com.sell.service;

import com.sell.dataobject.OrderMaster;
import com.sell.dataobject.ProductInfo;
import com.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
   /*创建订单*/
    OrderDTO creat(OrderDTO orderDTO);
    /*查询单个订单*/
    OrderDTO findOne(String orderid);
    /*查询订单列表*/
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);
    /*取消订单*/
    OrderDTO cancel(OrderDTO orderDTO);
    /*完结订单*/
    OrderDTO finish(OrderDTO orderDTO);
    /*支付订单*/
    OrderDTO paid(OrderDTO orderDTO);

    Page<OrderDTO> findList(Pageable pageable);
}
