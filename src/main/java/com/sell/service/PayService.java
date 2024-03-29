package com.sell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.sell.dto.OrderDTO;

public interface PayService {

 PayResponse create(OrderDTO orderDTO);

 PayResponse notify(String notifyData);

 RefundResponse refund(OrderDTO orderDTO);
}
