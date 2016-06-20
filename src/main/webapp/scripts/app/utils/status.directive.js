'use strict';

// angular.module('jeducenterApp')
//     .directive('fileUploader', function (fileService) {
//         return function (scope, element) {
//             element.bind('change', function () {
//                 fileService.setFile(element[0].files[0]);
//             });
//         }
//     });


angular.module('jeducenterApp')
    .directive('status', function () {
        return {
            restrict: 'E',
            scope: {
                value: '@'
            },
            controller: function ($scope) {
                $scope.calculate = function (input) {
                    console.log("Input is " + input);
                    if (input) {
                        return "Активен";
                    }
                    return "Архив";
                }
            },
            template: '<span>{{calculate(value)}}</span>'
        }
    });
