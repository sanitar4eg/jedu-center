'use strict';

angular.module('jeducenterApp')
    .controller('LessonDetailController', function ($scope, $rootScope, $stateParams, entity, Lesson, TimeTable, Evaluation) {
        $scope.lesson = entity;
        $scope.load = function (id) {
            Lesson.get({id: id}, function(result) {
                $scope.lesson = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:lessonUpdate', function(event, result) {
            $scope.lesson = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
