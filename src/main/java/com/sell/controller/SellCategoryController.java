package com.sell.controller;

import com.lly835.bestpay.rest.type.Get;
import com.sell.dataobject.ProductCategory;
import com.sell.exception.SellException;
import com.sell.form.CategoryForm;
import com.sell.service.CategoryService;
import com.sell.utils.KeyUntil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Key;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellCategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> productCategoryList=categoryService.findAll();
        map.put("productCategoryList",productCategoryList);
        map.put("url","/seller/category/list");
        return new ModelAndView("/category/list",map);
    }

    /*修改類目*/
    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer categoryId, Map<String, Object> map){
        if (!StringUtils.isEmpty(categoryId)){
            ProductCategory productCategory=categoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    /*新增*/
    @PostMapping("save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             Map<String,Object> map,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/seller/category/list");
            return new ModelAndView("common/error",map);
        }

        //判斷categoryId是否為空，為空則是新增
        ProductCategory productCategory=new ProductCategory();
        try{
            if (!StringUtils.isEmpty(categoryForm.getCategoryId())){   //若不為空
                productCategory=categoryService.findOne(categoryForm.getCategoryId());
            }

        BeanUtils.copyProperties(categoryForm,productCategory);
        categoryService.save(productCategory);
        }catch (SellException e){
            map.put("mag",e.getMessage());
            map.put("url","/seller/category/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/seller/category/list");
        return new ModelAndView("/common/success",map);
    }
}
