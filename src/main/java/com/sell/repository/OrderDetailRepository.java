package com.sell.repository;


import com.sell.dataobject.OrderDetail;
import com.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 廖师兄
 * 2017-05-07 14:35
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /*根据订单主表里面的orderID来查询详细订单表里的内容，一个订单里面可能包含多给食品*/
     List<OrderDetail> findByOrderId(String order);
}
