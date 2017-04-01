/**
 * Created by perfection on 17-3-27.
 */
(function () {
    'use strict';

    angular.module('DIE.pages.user')
        .controller('loginCtrl', loginCtrl);

    /** @ngInject */
    function loginCtrl($scope, baConfig, colorHelper,$http,URL) {

        $scope.transparent = baConfig.theme.blur;
        var dashboardColors = baConfig.colors.dashboard;

        $scope.userInfo = {
            account:"",
            password:""
        };
        $scope.signIn = function () {
            $http.post(URL+"api/login")
        }
    }
})();