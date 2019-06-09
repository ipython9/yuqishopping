<div id="nav-bottom">
	<!--顶部-->
	<div class="nav-top">
        <div class="top">
            <div class="py-container">
                <div class="shortcut">
                    <ul class="fl">
                        <li class="f-item">郁柒商城欢迎您！</li>
                        <li class="f-item" ng-if="loginName==anonymousUser">请<a href="http://localhost:9100/cas/login" target="_blank">登录</a>　<span><a href="http://localhost:9106/register.html" target="_blank">免费注册</a></span></li>
                        <li class="f-item" ng-if="loginName!='anonymousUser'">{{loginName}}</li>
                    </ul>
                    <ul class="fr">
                        <li class="f-item"><a href="http://localhost:9103/index.html" target="_blank">商城首页</a></li>
                        <li class="f-item space"></li>
                        <li class="f-item"><a href="http://localhost:9106/home-index.html" target="_blank">我的商城</a></li>
                        <li class="f-item space"></li>
                        <li class="f-item" id="service">
                            <span>客户服务</span>
                            <ul class="service">
                                <li><a href="http://localhost:9103/shoplogin.html" target="_blank">商家后台</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

		<!--头部-->
		<div class="header">
			<div class="py-container">
				<div class="yui3-g Logo">
					<div class="yui3-u Left logoArea">
						<a class="logo-bd" title="品优购" href="JD-index.html" target="_blank"></a>
					</div>
					<div class="yui3-u Center searchArea">
						<div class="search">
							<form action="" class="sui-form form-inline">
								<!--searchAutoComplete-->
                                <div class="input-append">
                                    <input type="text" id="autocomplete" ng-model="keywords" type="text" class="input-error input-xxlarge" />
                                    <a class="sui-btn btn-xlarge btn-danger" href="http://localhost:9104/search.html#?keywords={{keywords}}" type="button">搜索</a>
                                </div>
							</form>
						</div>
						<div class="hotwords">
							<#--<ul>-->
								<#--<li class="f-item">品优购首发</li>-->
								<#--<li class="f-item">亿元优惠</li>-->
								<#--<li class="f-item">9.9元团购</li>-->
								<#--<li class="f-item">每满99减30</li>-->
								<#--<li class="f-item">亿元优惠</li>-->
								<#--<li class="f-item">9.9元团购</li>-->
								<#--<li class="f-item">办公用品</li>-->

							<#--</ul>-->
						</div>
					</div>
					<div class="yui3-u Right shopArea">
						<div class="fr shopcar">
							<div class="show-shopcar" id="shopcar">
								<span class="car"></span>
								<a class="sui-btn btn-default btn-xlarge" href="cart.html" target="_blank">
									<span>我的购物车</span>
									<i class="shopnum">0</i>
								</a>
								<#--<div class="clearfix shopcarlist" id="shopcarlist" style="display:none">-->
									<#--<p>"啊哦，你的购物车还没有商品哦！"</p>-->
									<#--<p>"啊哦，你的购物车还没有商品哦！"</p>-->
								<#--</div>-->
							</div>
						</div>
					</div>
				</div>

				<div class="yui3-g NavList">
					<div class="yui3-u Left all-sort">
						<h4>产品详细信息</h4>
					</div>
					<div class="yui3-u Center navArea">
						<#--<ul class="nav">-->
							<#--<li class="f-item">服装城</li>-->
							<#--<li class="f-item">美妆馆</li>-->
							<#--<li class="f-item">品优超市</li>-->
							<#--<li class="f-item">全球购</li>-->
							<#--<li class="f-item">闪购</li>-->
							<#--<li class="f-item">团购</li>-->
							<#--<li class="f-item">有趣</li>-->
							<#--<li class="f-item"><a href="seckill-index.html" target="_blank">秒杀</a></li>-->
						<#--</ul>-->
					</div>
					<div class="yui3-u Right"></div>
				</div>
			</div>
		</div>
	</div>
</div>