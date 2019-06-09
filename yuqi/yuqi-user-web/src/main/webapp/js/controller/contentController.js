app.controller('contentController',function($scope,$http,contentService){
	
	$scope.contentList=[];//广告列表
	
	$scope.findByCategoryId=function(categoryId){
		contentService.findByCategoryId(categoryId).success(
			function(response){
				$scope.contentList[categoryId]=response;
			}
		);		
	}


	//搜索  （传递参数）
	$scope.search=function(){
		location.href="http://localhost:9104/search.html#?keywords="+$scope.keywords;
	}
        $scope.name=function(){
            $http.get("../content/name.do").success(
                function (response) {
                    $scope.loginName=response.loginName;
                }
            )
        }
    $scope.findContent=function(){
        $http.get("../content/findContent.do").success(
            function (response) {
                $scope.list=response;
            }
        )
    }
});