/**
 * Created by perfection on 17-3-27.
 */
(function () {
    'use strict';

    angular.module('DIE.pages.user')
        .controller('registerCtrl', registerCtrl);

    /** @ngInject */
    function registerCtrl($scope, baConfig, colorHelper,$http,URL) {

        $scope.transparent = baConfig.theme.blur;
        var dashboardColors = baConfig.colors.dashboard;

        $scope.userInfo={
            userName:"",
            account:"",
            password:""
        };
        $scope.signUp = function(){
            $http.post(URL+"user/register.do",$scope.userInfo).success(function (data) {
                console.log(data);
                if(data.code==200){
                    console.log(data);
                }else{
                   console.log(data.msg);
                }
            }).error(function (error) {
                console.log("连接异常!");
            });
        }
    }
})();