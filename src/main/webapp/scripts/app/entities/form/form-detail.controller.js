'use strict';

angular.module('jeducenterApp')
    .controller('FormDetailController', function ($scope, $rootScope, $stateParams, entity, Form, Student) {
        $scope.form = entity;
        $scope.load = function (id) {
            Form.get({id: id}, function(result) {
                $scope.form = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:formUpdate', function(event, result) {
            $scope.form = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
