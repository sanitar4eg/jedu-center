'use strict';

angular.module('jeducenterApp').controller('StudentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Student', 'User', 'GroupOfStudent', 'Curator', 'Form', 'ReasonForLeaving',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Student, User, GroupOfStudent, Curator, Form, ReasonForLeaving) {

        $scope.student = entity;
        $scope.users = User.query();
        $scope.groupofstudents = GroupOfStudent.query();
        $scope.curators = Curator.query();
        $scope.forms = Form.query({filter: 'student-is-null', isActive: true});
        $q.all([$scope.student.$promise, $scope.forms.$promise]).then(function() {
            if (!$scope.student.form || !$scope.student.form.id) {
                return $q.reject();
            }
            return Form.get({id : $scope.student.form.id}).$promise;
        }).then(function(form) {
            $scope.forms.push(form);
        });
        $scope.reasonforleavings = ReasonForLeaving.query({filter: 'student-is-null'});
        $q.all([$scope.student.$promise, $scope.reasonforleavings.$promise]).then(function() {
            if (!$scope.student.reasonForLeaving || !$scope.student.reasonForLeaving.id) {
                return $q.reject();
            }
            return ReasonForLeaving.get({id : $scope.student.reasonForLeaving.id}).$promise;
        }).then(function(reasonForLeaving) {
            $scope.reasonforleavings.push(reasonForLeaving);
        });
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
