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
                         <form role="form" method="post" action="/seller/category/save">

                             <div class="form-group">
                                 <label>名称</label>
                                 <input name="categoryName" type="text" class="form-control" value="${(productCategory.categoryName)!''}"/>
                             </div>
                             <div class="form-group">
                                 <label>type</label>
                                 <input name="categoryType" type="number" class="form-control" value="${(productCategory.categoryType)!''}"/>
                             </div>

                             <input hidden type="text" name="categoryId" value="${(productCategory.categoryId)!''}"><#--隐藏的productId字段-->

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