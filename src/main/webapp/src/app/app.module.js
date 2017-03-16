/**
 * Created by perfection on 17-3-16.
 */
(function () {
    'use strict';

    angular.module('DIE', [])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($urlRouterProvider, $stateProvider) {
        $urlRouterProvider.otherwise('/auth');
        console.log("Asdfsd")

        $stateProvider
            .state('auth', {
                url: '/auth',
                title: '登陆',
                templateUrl: 'auth.html',
                controller: 'authCtrl'
            });
    }

})();