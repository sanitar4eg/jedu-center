'use strict';

angular.module('jeducenterApp')
    .controller('StudentHistoryController', function ($scope, $state, StudentHistory) {

        var historyDate = new Date();

        var picker = $('#datetimepicker').datetimepicker({
            defaultDate: historyDate,
            locale: 'ru'
        });

        $scope.students = [];
        $scope.loadAll = function(dateTime) {
            var date = picker.data("DateTimePicker").date();
            StudentHistory.query({dateTime: date.toISOString()}, function(result) {
                $scope.students = result;
            });
        };
        $scope.loadAll();

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.student = {
                firstName: null,
                lastName: null,
                middleName: null,
                type: null,
                email: null,
                phone: null,
                university: null,
                specialty: null,
                course: null,
                groupName: null,
                id: null
            };
        };
    });
