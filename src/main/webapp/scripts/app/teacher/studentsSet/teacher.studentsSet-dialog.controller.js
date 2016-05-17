'use strict';

angular.module('jeducenterApp').controller('TeacherStudentsSetDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentsSet', 'Student', 'GroupOfStudent',
        function($scope, $stateParams, $uibModalInstance, entity, StudentsSet, Student, GroupOfStudent) {

        $scope.studentsSet = entity;
        $scope.students = Student.query();
        $scope.groupofstudents = GroupOfStudent.query();
        $scope.load = function(id) {
            StudentsSet.get({id : id}, function(result) {
                $scope.studentsSet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:studentsSetUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.studentsSet.id != null) {
                StudentsSet.update($scope.studentsSet, onSaveSuccess, onSaveError);
            } else {
                StudentsSet.save($scope.studentsSet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
