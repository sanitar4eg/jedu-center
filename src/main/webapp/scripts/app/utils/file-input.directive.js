'use strict';

angular.module('jeducenterApp')
    .directive('bfi', function (tmhDynamicLocale) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                element.fileinput({
                    language: tmhDynamicLocale.get()
                });
            }
        }
    });
