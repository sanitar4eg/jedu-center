'use strict';

angular.module('jeducenterApp').controller('SetStudentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Student', 'User', 'GroupOfStudent', 'Curator',
        'Form', 'StudentsSet', 'LearningType', 'LearningResult',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Student, User, GroupOfStudent, Curator, Form,
                 StudentsSet, LearningType, LearningResult) {

        $scope.student = entity;
            console.log($scope.student);
        $scope.groupofstudents = GroupOfStudent.query({isActive: true});
        $scope.curators = Curator.query({isActive: true});
        $scope.forms = Form.query({filter: 'student-is-null', isActive: true});
        $scope.studentssets = StudentsSet.query();
        $scope.learningtypes = LearningType.query();
        $scope.learningresults = LearningResult.query();
        $q.all([$scope.student.$promise, $scope.forms.$promise]).then(function() {
            if (!$scope.student.form || !$scope.student.form.id) {
                return $q.reject();
            }
            return Form.get({id : $scope.student.form.id}).$promise;
        }).then(function(form) {
            $scope.forms.push(form);
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
