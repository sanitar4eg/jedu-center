'use strict';

angular.module('jeducenterApp').controller('StudentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Student', 'User', 'GroupOfStudent', 'Curator', 'Form',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Student, User, GroupOfStudent, Curator, Form) {

        $scope.student = entity;
        $scope.users = User.query();
        $scope.groupofstudents = GroupOfStudent.query();
        $scope.curators = Curator.query();
        $scope.forms = Form.query();
        $scope.load = function(id) {
            Student.get({id : id}, function(result) {
                $scope.student = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:studentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.student.id != null) {
                Student.update($scope.student, onSaveSuccess, onSaveError);
            } else {
                Student.save($scope.student, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
