/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('DIE.pages.dashboard', [])
      .config(routeConfig).controller("dashboardCtr",dashboardCtr);

  /** @ngInject */
  function routeConfig($stateProvider) {
    $stateProvider
        .state('dashboard', {
          url: '/dashboard',
          templateUrl: 'app/pages/dashboard/dashboard.html',
          title: 'Dashboard',
          sidebarMeta: {
            icon: 'ion-android-home',
            order: 0,
          },
            controller:"dashboardCtr"
        });
  }

  function dashboardCtr($location) {
      console.log("进入首页检测")
      // $location.path("/login")
  }

})();
