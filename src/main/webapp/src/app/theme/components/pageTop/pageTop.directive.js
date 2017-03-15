/**
 * @author v.lugovksy
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('DIE.theme.components')
      .directive('pageTop', pageTop);

  /** @ngInject */
  function pageTop(URL,$http) {
    return {
      restrict: 'E',
      templateUrl: 'app/theme/components/pageTop/pageTop.html',
      link:function(){
        $http.post(URL+"");
      }
    };
  }

})();