'use strict';

angular.module('jeducenterApp')
    .controller('TeacherTimeTableDetailController', function ($scope, $rootScope, $stateParams, entity, TimeTable
        , GroupOfStudent, Lesson, Student, Evaluation, $translate) {
        $scope.timeTable = entity;
        $scope.students = [];
        $scope.lessons = [];
        $scope.evaluations = [];
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


        $scope.evaluationChanged = function (evaluation) {
            console.log("CHANGED!!!!!!");
            $scope.isSaving = true;
            if (evaluation.id != null) {
                Evaluation.update(evaluation, onSaveSuccess, onSaveError);
            } else {
                Evaluation.save(evaluation, onSaveSuccess, onSaveError);
            }
        };
        var onSaveSuccess = function (result) {
            $scope.searchEvaluation(result.student, result.lesson).id = result.id;
            $scope.$emit('jeducenterApp:evaluationUpdate', result);
            $scope.isSaving = false;
        };
        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.searchEvaluation = function (student, lesson){
            for (var i=0; i < $scope.evaluations.length; i++) {
                if ($scope.evaluations[i].student.id === student.id && $scope.evaluations[i].lesson.id === lesson.id) {
                    return $scope.evaluations[i];
                }
            }
            return $scope.evaluations.push({value:null, student:student, lesson: lesson});
        };

        $scope.load = function (id) {
            TimeTable.get({id: id}, function(result) {
                $scope.timeTable = result;
            });
            Lesson.query({timetable: $scope.timeTable.id}, function (result) {
                $scope.lessons = result;
            });
            Student.query(function (result) {
                $scope.students = result;
            });
            Evaluation.query(function (result) {
                $scope.evaluations = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:timeTableUpdate', function(event, result) {
            $scope.timeTable = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.load($stateParams.id);

    });
