package com.sell.repository;


import com.sell.dataobject.OrderMaster;
import com.sell.dataobject.ProductInfo;
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
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {


    /*分页查询订单表根据用户的opendid*/
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

    OrderMaster findByOrderId(String orderId);

}
