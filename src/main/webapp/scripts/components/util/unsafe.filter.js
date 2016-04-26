'use strict';

angular.module('jeducenterApp').filter('unsafe', function ($sce) {
    return $sce.trustAsHtml;
});
