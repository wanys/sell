package com.sell.service;

import com.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuyerService {
 //查询一个订单
 OrderDTO findOrderOne(String openid, String orderId);

 //取消订单
 OrderDTO cancelOrder(String openid, String orderId);
}
