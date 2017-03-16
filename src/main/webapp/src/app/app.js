'use strict';

angular.module('DIE', [
  'ngAnimate',
  'ui.bootstrap',
  'ui.sortable',
  'ui.router',
  'ngTouch',
  'toastr',
  'smart-table',
  "xeditable",
  'ui.slimscroll',
  'ngJsTree',
  'angular-progress-button-styles',

  'DIE.theme',
  'DIE.pages'
]).constant('URL', "http://192.168.2.38:8080/").config(routeConfig);

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
};