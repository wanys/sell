package com.sell.repository;

import com.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.security.PublicKey;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void saveTest(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("m3");
        orderMaster.setBuyerName("王");
        orderMaster.setBuyerPhone("18211112222");
        orderMaster.setBuyerAddress("杭师大");
        orderMaster.setBuyerOpenid("113");
        orderMaster.setOrderAmount(new BigDecimal(23));
       OrderMaster result= orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }


    @Test
    public void findByBuyerOpendid() {
    }
}