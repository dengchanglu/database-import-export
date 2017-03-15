/**
 * @author 邓昌路
 * @description 个人信息的头像获取(使用过滤器)
 * Created on 17-3-15.
 */
(function () {
  'use strict';

  angular.module('DIE.theme')
      .filter('profilePicture', profilePicture);

  /** @ngInject */
  function profilePicture(layoutPaths) {
    return function(input, ext) {
      ext = ext || 'png';
      return layoutPaths.images.profile + input + '.' + ext;
    };
  }

})();
