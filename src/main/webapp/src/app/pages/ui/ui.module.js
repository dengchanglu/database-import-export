/**
 * @author k.danovsky
 * created on 12.01.2016
 */
(function () {
  'use strict';

  angular.module('DIE.pages.ui', [
    'DIE.pages.ui.typography',
    'DIE.pages.ui.buttons',
    'DIE.pages.ui.icons',
    'DIE.pages.ui.modals',
    'DIE.pages.ui.grid',
    'DIE.pages.ui.alerts',
    'DIE.pages.ui.progressBars',
    'DIE.pages.ui.notifications',
    'DIE.pages.ui.tabs',
    'DIE.pages.ui.slider',
    'DIE.pages.ui.panels',
  ])
      .config(routeConfig);

  /** @ngInject */
  function routeConfig($stateProvider) {
    $stateProvider
        .state('ui', {
          url: '/ui',
          template : '<ui-view  autoscroll="true" autoscroll-body-top></ui-view>',
          abstract: true,
          title: 'UI Features',
          sidebarMeta: {
            icon: 'ion-android-laptop',
            order: 200,
          },
        });
  }

})();
