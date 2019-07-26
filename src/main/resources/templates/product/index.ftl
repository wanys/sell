 <html>
 <#include "*/../common/header.ftl">
 <body>

 <div id="wrapper" class="toggled">
     <#--边栏sider-->
      <#--   <#include "D:/IdeaProjects/sell/target/classes/templates/common/nav.ftl">-->
          <#include "*/../common/nav.ftl">

     <#--主要內容content-->
     <div id="page-content-wrapper">
         <div class="container-fluid">
             <div class="container">
                 <div class="row clearfix">
                     <div class="col-md-12 column">
                         <form role="form" method="post" action="/seller/product/save">
                             <div class="form-group">
                                 <label>名称</label>
                                 <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}"/>
                             </div>
                             <div class="form-group">
                                 <label>价格</label>
                                 <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!''}"/>
                             </div>
                             <div class="form-group">
                                 <label>库存</label>
                                 <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}"/>
                             </div>
                             <div class="form-group">
                                 <label>描述</label>
                                 <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}"/>
                             </div>
                             <div class="form-group">
                                 <label>图片</label>
                                 <input id="productIcon" name="productIcon" type="text" ; value="${(productInfo.productIcon)!''}"/>
                                 <div class="file-loading">
                                     <input id="input-id" type="file">
                                     <p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过1M</p>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label>类目</label>
                                 <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                            <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                                selected
                                            </#if>
                                    >${category.categoryName}
                                    </option>
                                </#list>
                                 </select>
                             </div>
                             <input hidden type="text" name="productId" value="${(productInfo.productId)!''}"><#--隐藏的productId字段-->

                                 <button type="submit" class="btn btn-default">Submit</button>
                         </form>
                     </div>
                 </div>
             </div>
         </div>
     </div>
 </div>


 </body>
 </html>