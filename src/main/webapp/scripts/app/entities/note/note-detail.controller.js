'use strict';

angular.module('jeducenterApp')
    .controller('NoteDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Note) {
        $scope.note = entity;
        $scope.load = function (id) {
            Note.get({id: id}, function(result) {
                $scope.note = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:noteUpdate', function(event, result) {
            $scope.note = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
