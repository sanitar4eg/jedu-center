'use strict';

angular.module('jeducenterApp').controller('GroupOfStudentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'GroupOfStudent', 'Student', 'TimeTable',
        function($scope, $stateParams, $uibModalInstance, $q, entity, GroupOfStudent, Student, TimeTable) {

        $scope.groupOfStudent = entity;
        $scope.students = Student.query();
        $scope.timetables = TimeTable.query({filter: 'groupofstudent-is-null'});
        $q.all([$scope.groupOfStudent.$promise, $scope.timetables.$promise]).then(function() {
            if (!$scope.groupOfStudent.timeTable || !$scope.groupOfStudent.timeTable.id) {
                return $q.reject();
            }
            return TimeTable.get({id : $scope.groupOfStudent.timeTable.id}).$promise;
        }).then(function(timeTable) {
            $scope.timetables.push(timeTable);
        });
        $scope.load = function(id) {
            GroupOfStudent.get({id : id}, function(result) {
                $scope.groupOfStudent = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:groupOfStudentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.groupOfStudent.id != null) {
                GroupOfStudent.update($scope.groupOfStudent, onSaveSuccess, onSaveError);
            } else {
                GroupOfStudent.save($scope.groupOfStudent, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
