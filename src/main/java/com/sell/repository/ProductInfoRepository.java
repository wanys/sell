package com.sell.repository;


import com.sell.dataobject.ProductCategory;
import com.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 廖师兄
 * 2017-05-07 14:35
 */
@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /*根据状态搜索商品*/
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
