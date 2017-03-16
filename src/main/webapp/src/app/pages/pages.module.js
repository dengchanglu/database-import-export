/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('DIE.pages', [
    'ui.router',

    'DIE.pages.dashboard',
    'DIE.pages.ui',
    'DIE.pages.components',
    'DIE.pages.form',
    'DIE.pages.tables',
    'DIE.pages.charts',
    'DIE.pages.maps',
    'DIE.pages.profile',
    'DIE.pages.user'

  ])
      .config(routeConfig);

  /** @ngInject */
  function routeConfig($urlRouterProvider, baSidebarServiceProvider) {
    // $urlRouterProvider.otherwise('/dashboard');

    baSidebarServiceProvider.addStaticItem({
      title: 'Pages',
      icon: 'ion-document',
      subMenu: [{
        title: '登陆',
        fixedHref: 'auth.html',
        blank: true,
        controller:"authCtr"
      }, {
        title: '注册',
        fixedHref: 'reg.html',
        blank: true
      }, {
        title: '用户资料',
        stateRef: 'profile'
      }, {
        title: '404 Page',
        fixedHref: '404.html',
        blank: true
      }]
    });
    baSidebarServiceProvider.addStaticItem({
      title: 'Menu Level 1',
      icon: 'ion-ios-more',
      subMenu: [{
        title: 'Menu Level 1.1',
        disabled: true
      }, {
        title: 'Menu Level 1.2',
        subMenu: [{
          title: 'Menu Level 1.2.1',
          disabled: true
        }]
      }]
    });
  }

})();
