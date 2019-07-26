package com.sell.service.impl;

import com.sell.dataobject.SellerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
public class SellerServiceImplTest {

    @Autowired
    private SellerServiceImpl sellerServiceImp;

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo result = sellerServiceImp.findSellerInfoByOpenid("abc");
        Assert.assertEquals("abc",result.getOpenid());
    }
}