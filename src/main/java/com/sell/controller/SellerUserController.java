package com.sell.controller;


import com.sell.dataobject.SellerInfo;
import com.sell.enums.ResultEnum;
import com.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/*卖家用户*/
@Controller
@RequestMapping("seller+")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("login")
    public ModelAndView login(@RequestParam("openid") String openid, Map<String,Object> map){
        /*  1.openid去和数据库里的数据匹配*/
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo==null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getCode());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error");
        }
        /*2.设置token至redis*/
        stringRedisTemplate.opsForValue().set("abc","wwwwww");  //要操作某些方法
        /*3.设置token至cookie*/
        return null;
    }

    @GetMapping("logout")
    public void logout(@RequestParam("openid") String openid){


    }
}
