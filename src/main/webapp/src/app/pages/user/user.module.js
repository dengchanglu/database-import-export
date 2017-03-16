/**
 * Created by perfection on 17-3-16.
 */
(function () {
    'use strict';

    angular.module('DIE.pages.user', [])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('login', {
                url: '/login',
                title: '登陆',
                templateUrl: 'app/pages/user/login.html',
                // controller: 'loginCtrl'
            })
            .state('register', {
                url: '/register',
                title: '注册',
                templateUrl: 'app/pages/user/register.html',
                controller: 'registerCtrl'
            });
    }

})();