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
]).constant('URL', "http://192.168.2.38:8080/");