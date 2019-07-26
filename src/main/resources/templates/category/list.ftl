 <html>
 <#include "*/common/header.ftl">
 <body>

 <div id="wrapper" class="toggled">
     <#--边栏sider-->
      <#--   <#include "D:/IdeaProjects/sell/target/classes/templates/common/nav.ftl">-->
          <#include "*/common/nav.ftl">

     <#--主要內容content-->
     <div id="page-content-wrapper">
         <div class="container-fluid">
             <div class="row clearfix">
                 <div class="col-md-12 column">
                             <table class="table table-bordered">
                                 <thead>
                                 <tr>
                                     <th>类目id</th>
                                     <th>名称</th>
                                     <th>type</th>
                                     <th>创建时间</th>
                                     <th>修改时间</th>
                                     <th  >操作</th>
                                 </tr>
                                 </thead>
                                 <tbody>
                         <#list productCategoryList as productCategory>
                         <tr>
                             <td>${productCategory.categoryId}</td>
                             <td>${productCategory.categoryName}</td>
                             <td>${productCategory.categoryType}</td>
                             <td>${productCategory.createTime}</td>
                             <td>${productCategory.updateTime}</td>
                             <td><a href="/seller/category/index?categoryId=${productCategory.categoryId}">修改</a></td>

                         </tr>
                         </#list>
                                 </tbody>
                             </table>
                 </div>

                 <#--分頁-->
              <#--   <div class="col-md-12 column">
                     <ul class="pagination pull-right">
                 <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                 <#else>
                        <li><a href="/seller/category/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                 </#if>

                    <#list 1..productCategory.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/seller/category/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>
                   <#if currentPage gte productCategory.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                   <#else>
                        <li><a href="/seller/category/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                   </#if>
                     </ul>
                 </div>-->
             </div>
         </div>
     </div>
 </div>


 </body>
 </html>