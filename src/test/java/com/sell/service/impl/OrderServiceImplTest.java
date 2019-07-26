package com.sell.service.impl;

import com.sell.dataobject.OrderDetail;
import com.sell.dto.OrderDTO;
import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String ORDER_ID="m156302760956420923";
    private final String BUYER_OPENID="113";
    @Test
    public void creatOrder() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("霜");
        orderDTO.setBuyerPhone("18244445555");
        orderDTO.setBuyerAddress("仓前街道");
        orderDTO.setBuyerOpenid("113");
        //购物车
        List<OrderDetail> orderDetailList=new ArrayList<>();
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setProductId("p3");
        orderDetail.setProductQuantity(10);
        orderDetailList.add(orderDetail);

        OrderDetail orderDetail2=new OrderDetail();
        orderDetail2.setProductId("p2");
        orderDetail2.setProductQuantity(10);
        orderDetailList.add(orderDetail2);

        orderDTO.setOrderDetailsList(orderDetailList);

        OrderDTO result= orderService.creat(orderDTO);
        log.info("【创建订单】result={}",result);
    }

    @Test
    public void findOne() {
        OrderDTO result=orderService.findOne(ORDER_ID);
        log.info("查询单个订单 result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());

    }

    @Test
    public void findList() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO resultDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.cancel(resultDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
     }

    @Test
    public void paid() {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
}