<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<title>产品详情页</title>
    <link rel="icon" href="img/assets/img/favicon.ico">

    <link rel="stylesheet" type="text/css" href="css/webbase.css" />
    <link rel="stylesheet" type="text/css" href="css/pages-item.css" />
    <link rel="stylesheet" type="text/css" href="css/pages-zoom.css" />
    <link rel="stylesheet" type="text/css" href="css/widget-cartPanelView.css" />
    
    <script type="text/javascript" src="plugins/angularjs/angular.min.js"> </script>
    <script type="text/javascript" src="js/base.js"> </script>
    <script type="text/javascript" src="js/controller/itemController.js"> </script>
    <script>
        var skuList=[
          <#list itemList as item>
           {
             id:${item.id?c},
             title:'${item.title}',
             price:${item.price?c},
             spec:${item.spec}
           } ,
          </#list>   
        ];
        
    </script>
</head>

<body ng-app="pinyougou" ng-controller="itemController" ng-init="num=1;loadSku();name()">

<!--页面顶部 开始-->
<#include "head.ftl">
<#--图片列表-->
<#assign imageList=goodsDesc.itemImages?eval>
<#--扩展属性-->
<#assign customAttributeList=goodsDesc.customAttributeItems?eval>
<#--规格-->
<#assign specificationList=goodsDesc.specificationItems?eval>


<!--页面顶部 结束-->
	<div class="py-container">
		<div id="item">
			<div class="crumb-wrap">
				<ul class="sui-breadcrumb">
					<li>
						<a href="http://localhost:9104/search.html#?keywords=${itemCat1}">${itemCat1}</a>
					</li>
					<li>
						<a href="http://localhost:9104/search.html#?keywords=${itemCat1}">${itemCat2}</a>
					</li>
					<li>
						<a href="http://localhost:9104/search.html#?keywords=${itemCat1}">${itemCat3}</a>
					</li>	
							
				</ul>
			</div>
			<!--product-info-->
			<div class="product-info">
				<div class="fl preview-wrap">
					<!--放大镜效果-->
					<div class="zoom">
						<!--默认第一个预览-->
						<div id="preview" class="spec-preview">
							<span class="jqzoom">
							 <#if (imageList?size>0)>
							     <img jqimg="${imageList[0].url}" src="${imageList[0].url}" width="400px" height="400px" />
							 </#if>
							</span>
						</div>
						<!--下方的缩略图-->
						<div class="spec-scroll">
							<a class="prev">&lt;</a>
							<!--左右按钮-->
							<div class="items">
								<ul>
								   <#list imageList as item>
                                       <li><img src="${item.url}" bimg="${item.url}" onmousemove="preview(this)" /></li>
								   </#list>
                            	</ul>
							</div>
							<a class="next">&gt;</a>
						</div>
					</div>
				</div>
				<div class="fr itemInfo-wrap">
					<div class="sku-name">
						<h4>{{sku.title}}</h4>
					</div>
					<div class="news"><span>${goods.caption}</span></div>
					<div class="summary">
						<div class="summary-wrap">
							<div class="fl title">
								<i>价　　格</i>
							</div>
							<div class="fl price">
								<i>¥</i>
								<em>{{sku.price}}</em>
							</div>
						</div>
					</div>
					<div class="support">

					</div>
					<div class="clearfix choose">
						<div id="specification" class="summary-wrap clearfix">
							<#list specificationList as spec>
							
							  <dl>
								<dt>
									<div class="fl title">
									<i>${spec.attributeName}</i>
								</div>
								</dt>
								
								<#list spec.attributeValue as item>
								  <dd><a href="javascript:;" 
								        class="{{isSelected('${spec.attributeName}','${item}')?'selected':''}}" 
								        ng-click="selectSpecification('${spec.attributeName}','${item}')">${item}
								        <span title="点击取消选择">&nbsp;</span>
								        </a></dd>
								</#list>
							  </dl>
							</#list>
							
						</div>
					
						<div class="summary-wrap">
							<div class="fl title">
								<div class="control-group">
									<div class="controls">
										<input autocomplete="off" ng-model="num" type="text" value="1" minnum="1" class="itxt" />
										<a href="javascript:void(0)" class="increment plus" ng-click="addNum(1)">+</a>
										<a href="javascript:void(0)" class="increment mins" ng-click="addNum(-1)">-</a>
									</div>
								</div>
							</div>
							<div class="fl">
								<ul class="btn-choose unstyled">
									<li>
										<a  class="sui-btn  btn-danger addshopcar" ng-click="addToCart()">加入购物车</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--product-detail-->
			<div class="clearfix product-detail">
				</div>
				<div class="fr detail">

					<div class="tab-main intro">
						<ul class="sui-nav nav-tabs tab-wraped">
							<li class="active">
								<a href="#one" data-toggle="tab">
									<span>商品介绍</span>
								</a>
							</li>
							<li>
								<a href="#two" data-toggle="tab">
									<span>规格与包装</span>
								</a>
							</li>
							<li>
								<a href="#three" data-toggle="tab">
									<span>售后保障</span>
								</a>
							</li>

						</ul>
						<div class="clearfix"></div>
						<div class="tab-content tab-wraped">
							<div id="one" class="tab-pane active">

								<div class="intro-detail">
									${goodsDesc.introduction}
								</div>
							</div>
							<div id="two" class="tab-pane">
                                <ul class="goods-intro unstyled">
								   <#list customAttributeList as item>
								      <#if item.value??>
									      <li>${item.text}：${item.value}</li>
									  </#if>
								   </#list>
                                </ul>
								<p>${goodsDesc.packageList}</p>
							</div>
							<div id="three" class="tab-pane">
								<p>${goodsDesc.saleService}</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--like-->
		</div>
	</div>
	<!-- 底部栏位 -->
	
<!--页面底部  开始 -->
<#include "foot.ftl">
<!--页面底部  结束 -->
</body>

</html>