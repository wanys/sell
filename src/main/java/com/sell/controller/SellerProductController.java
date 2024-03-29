package com.sell.controller;

import com.sell.dataobject.ProductCategory;
import com.sell.dataobject.ProductInfo;
import com.sell.dto.OrderDTO;
import com.sell.exception.SellException;
import com.sell.form.ProductForm;
import com.sell.service.CategoryService;
import com.sell.service.ProductService;
import com.sell.utils.KeyUntil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

/*
* 卖家端商品
* */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    /*列表*/
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "3") Integer size,
                             Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
//        orderDTOPage.getTotalPages()
        return new ModelAndView("product/list", map);
    }

    @RequestMapping("on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,Map<String,Object> map){
        try{
            productService.onSale(productId);
        }catch (SellException e){
           map.put("mag",e.getMessage());
           map.put("url","/seller/product/list");
           return new ModelAndView("common/error",map);
        }

          map.put("url","/seller/product/list");
            return new ModelAndView("common/success",map);

    }
    @RequestMapping("off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,Map<String,Object> map){
        try{
            productService.offSale(productId);
        }catch (SellException e){
            map.put("mag",e.getMessage());
            map.put("url","/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @RequestMapping("index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,Map<String,Object> map){
        if (!StringUtils.isEmpty(productId)){
            ProductInfo productInfo=productService.findOne(productId);
            map.put("productInfo",productInfo);
        }

        //查询类目
        List<ProductCategory> categoryList= categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("product/index",map);
    }
/*
* 保存或者更新
* */
    @PostMapping("save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        ProductInfo productInfo=new ProductInfo();
        try{
            //如果produceId是空。则是新增
            if (!StringUtils.isEmpty(productForm.getProductId())){     //若不為空
                  productInfo=productService.findOne(productForm.getProductId());
            }
            else {
                productForm.setProductId(KeyUntil.genUniqueKey());
            }
            BeanUtils.copyProperties(productForm,productInfo);

            productService.save(productInfo);
        }catch (SellException e){
            map.put("mag",e.getMessage());
            map.put("url","/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/seller/product/list");
        return new ModelAndView("/common/success",map);
    }
}
