package com.sell.service.impl;

import com.sell.VO.ProductVO;
import com.sell.converter.OrderMaster2OrderDTOConverter;
import com.sell.dataobject.OrderDetail;
import com.sell.dataobject.OrderMaster;
import com.sell.dataobject.ProductCategory;
import com.sell.dataobject.ProductInfo;
import com.sell.dto.CarDTO;
import com.sell.dto.OrderDTO;
import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import com.sell.enums.ResultEnum;
import com.sell.exception.SellException;
import com.sell.repository.OrderDetailRepository;
import com.sell.repository.OrderMasterRepository;
import com.sell.service.OrderService;
import com.sell.service.ProductService;
import com.sell.utils.KeyUntil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    public ProductService productService;

    @Autowired
    public OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO creat(OrderDTO orderDTO) {

        String orderlId="m"+ KeyUntil.genUniqueKey();
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);
     //   List<CarDTO> carDTOList=new ArrayList<>();
        //1 查询商品
        for (OrderDetail orderDetail:orderDTO.getOrderDetailsList()){
            ProductInfo productInfo=productService.findOne(orderDetail.getProductId());
            if (productInfo==null){
                throw new SecurityException(ResultEnum.PRODUCT_NOT_EXIST.getMessage());
            }
            /* 2  计算总价*/
            orderAmount=  productInfo.getProductPrice()
                           .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                           .add(orderAmount);


            orderDetail.setOrderId(orderlId);
            orderDetail.setDetailId("d"+ KeyUntil.genUniqueKey() );
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

        /*    CarDTO carDTO=new CarDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            carDTOList.add(carDTO);*/

        }
        /*3 写入订单数据库（orderMaster和OrderDetail)*/
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderlId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        /*4 扣库存*/
        List<CarDTO> carDTOList=new ArrayList<>();
       carDTOList= orderDTO.getOrderDetailsList().stream().map(e ->new CarDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());

        productService.decreaseStock(carDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderid) {
     //   Optional<OrderMaster> orderMaster=orderMasterRepository.findById(orderid);
        //Optional<OrderMaster> orderMaster = orderMasterRepository.findById(orderid);
        OrderMaster orderMaster=orderMasterRepository.findByOrderId(orderid);
        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

       List<OrderDetail> orderDetailList=new ArrayList<>();
        orderDetailList=orderDetailRepository.findByOrderId(orderid);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailsList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage=new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            System.out.println("【取消订单状态不正确】，orderId={}，orderStatus={}"+orderDTO.getOrderId()+orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster UpdataResult = orderMasterRepository.save(orderMaster);
        if (UpdataResult==null){
            System.out.println("【完结订单更新失败】，orderMaster{}"+orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailsList())){
            System.out.println("【取消订单】中无商品,orderDTO="+orderDTO);
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        List<CarDTO> carDTOList=orderDTO.getOrderDetailsList().stream()
                .map(e ->new CarDTO(e.getProductId(),e.getProductQuantity()))
               .collect(Collectors.toList());
        productService.increaseStock(carDTOList);
        //如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            System.out.println("【完结订单不正确】，orderId={}，orderStatus={}"+orderDTO.getOrderId()+orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResul=orderMasterRepository.save(orderMaster);
        if (updateResul==null){
            System.out.println("【完结订单更新失败】，orderMaster{}"+orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

            return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            System.out.println("【订单支付完成】订单支付不正确，orderId={}，orderStatus={}"+orderDTO.getOrderId()+orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            System.out.println("【订单支付完成】订单支付不正确，orderId={}，orderStatus={}"+orderDTO );
            throw new SellException(ResultEnum.ORDER_PAY_STATUS);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            System.out.println("【支付订单更新失败】，orderMaster{}"+orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());

    }
}
