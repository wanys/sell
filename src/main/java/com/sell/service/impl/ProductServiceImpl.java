package com.sell.service.impl;


import com.sell.config.UpYunConfig;
import com.sell.dataobject.ProductInfo;
import com.sell.dto.CarDTO;
import com.sell.enums.ProductStatusEnum;
import com.sell.enums.ResultEnum;
import com.sell.exception.SellException;
import com.sell.repository.ProductInfoRepository;
import com.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private UpYunConfig upYunConfig;
    @Override
    public ProductInfo findOne(String id) {
        return productInfoRepository.getOne(id);
    }

    @Override
    public List<ProductInfo> findUPAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
      //  return productInfoRepository.findByProductStatus(0);

    }

 /*   @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        Page<ProductInfo> productInfoPage = productInfoRepository.findAll(pageable);
        productInfoPage.getContent().stream()
                .forEach(e -> e.addImageHost(upYunConfig.getImageHost()));
        return productInfoPage;
    }*/

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {

        return productInfoRepository.findAll(pageable );
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);

    }

    @Override
    public void increaseStock(List<CarDTO> carDTOList) {
        for (CarDTO carDTO:carDTOList) {
            ProductInfo productInfo = productInfoRepository.getOne(carDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=  productInfo.getProductStock()+carDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }

    }

    @Override
    public void decreaseStock(List<CarDTO> carDTOList) {
        for (CarDTO carDTO:carDTOList){
            ProductInfo productInfo = productInfoRepository.getOne(carDTO.getProductId());
            if (productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);

            }
            Integer result=  productInfo.getProductStock()-carDTO.getProductQuantity();
            if (result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoRepository.getOne(productId);
        if (productInfo==null){
            throw new  SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum()==ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        ProductInfo productInfo1=productInfoRepository.save(productInfo);
        System.out.println(productInfo1.getProductStock());
        return productInfo1;
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoRepository.getOne(productId);
        if (productInfo==null){
            throw new  SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum()==ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStock(ProductStatusEnum.DOWN.getCode());
        System.out.println();
        return productInfoRepository.save(productInfo);

    }


}
