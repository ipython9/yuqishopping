 //控制层 
app.controller('userController' ,function($scope,$http,$location,$controller,uploadService,userService){
	
	//注册用户
	$scope.reg=function(){
		
		//比较两次输入的密码是否一致
		if($scope.password!=$scope.entity.password){
			alert("两次输入密码不一致，请重新输入");
			$scope.entity.password="";
			$scope.password="";
			return ;			
		}
		//新增
		userService.add($scope.entity).success(
			function(response){

                location.href="http://localhost:9106";
			}		
		);
	}
    //上传图片
    $scope.uploadFile=function(){
        uploadService.uploadFile().success(
            function(response){
                if(response.success){
                    $scope.image_entity.url= response.message;
                }else{
                    alert(response.message);
                }
            }
        );


    }
	// //发送验证码
	// $scope.sendCode=function(){
	// 	if($scope.entity.phone==null || $scope.entity.phone==""){
	// 		alert("请填写手机号码");
	// 		return ;
	// 	}
	//
	// 	userService.sendCode($scope.entity.phone  ).success(
	// 		function(response){
	// 			alert(response.message);
	// 		}
	// 	);
	//}
    $scope.showName=function(){
        $http.get('../login/name.do').success(
        	function (response) {
        		$scope.loginName=response.loginName;
            }
		)
    }



    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        userService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    //分页
    $scope.findPage=function(page,rows){
        userService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne=function(id){
        userService.findOne(id).success(
            function(response){
                $scope.entity= response;
            }
        );
    }

    //保存
    $scope.save=function(){
        var serviceObject;//服务层对象
        if($scope.entity.id!=null){//如果有ID
            serviceObject=userService.update( $scope.entity ); //修改
        }else{
            serviceObject=userService.add( $scope.entity  );//增加
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    //重新查询
                    $scope.reloadList();//重新加载
                }else{
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele=function(){
        //获取选中的复选框
        userService.dele( $scope.selectIds ).success(
            function(response){
                if(response.success){
                    $scope.reloadList();//刷新列表
                    $scope.selectIds=[];
                }
            }
        );
    }

    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        userService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

});	
