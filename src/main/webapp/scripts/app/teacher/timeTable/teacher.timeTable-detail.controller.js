'use strict';

angular.module('jeducenterApp')
    .controller('TeacherTimeTableDetailController', function ($scope, $rootScope, $stateParams, entity, TimeTable
        , GroupOfStudent, Lesson, Student, $translate) {
        $scope.timeTable = entity;
        $scope.students = [];
        $scope.columns = [
            {
                displayName: 'jeducenterApp.student.firstName', field: 'firstName', width: '10%',
                headerCellFilter: "translate"
            },
            {
                displayName: 'jeducenterApp.student.middleName', field: 'middleName', width: '12%',
                headerCellFilter: "translate"
            },
            {
                displayName: 'jeducenterApp.student.lastName', field: 'lastName', width: '12%',
                headerCellFilter: "translate"
            }
        ];

        function search(nameKey, myArray){
            for (var i=0; i < myArray.length; i++) {
                console.log("Name is " + nameKey);
                if (myArray[i].id === nameKey) {
                    return myArray[i];
                }
            }
        }

        $scope.load = function (id) {
            TimeTable.get({id: id}, function(result) {
                $scope.timeTable = result;
                console.log(JSON.stringify($scope.timeTable.lessons));
                for (var i = 0; i < result.lessons.length; i++) {
                    console.log('Added ' + result.lessons[i].title);
                    $scope.columns.push({
                        name: result.lessons[i].topic,
                        cellTemplate: '<p>{{search(row.entity.id,$scope.timeTable.lessons[i].evaluations)}}</p>'
                    });
                }
            });
            Student.query(function (result) {
                $scope.students = result;
                $scope.timeTableGrid.data = result;

            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:timeTableUpdate', function(event, result) {
            $scope.timeTable = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.load($stateParams.id);

        $scope.timeTableGrid = {
            columnDefs: $scope.columns

        };

    });
