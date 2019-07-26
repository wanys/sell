package com.sell.service;


import com.sell.dataobject.ProductInfo;
import com.sell.dto.CarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {
    ProductInfo findOne(String id);
    /*查询所有在架商品列表*/
    List<ProductInfo> findUPAll();
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);
    //List<ProductInfo> findByProductStatus(Integer productStatus);

    //加库存
    void increaseStock(List<CarDTO> carDTOList);

    /*减库存*/
    void decreaseStock(List<CarDTO> carDTOList);

    /*上架*/
    ProductInfo onSale(String productId);

    /*下架*/
    ProductInfo offSale(String productId);
}
