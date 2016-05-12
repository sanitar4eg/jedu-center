'use strict';

angular.module('jeducenterApp').controller('ReasonForLeavingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ReasonForLeaving', 'Student',
        function($scope, $stateParams, $uibModalInstance, $q, entity, ReasonForLeaving, Student) {

        $scope.reasonForLeaving = entity;
        $scope.students = Student.query({filter: 'reasonforleaving-is-null'});
        $q.all([$scope.reasonForLeaving.$promise, $scope.students.$promise]).then(function() {
            if (!$scope.reasonForLeaving.student || !$scope.reasonForLeaving.student.id) {
                return $q.reject();
            }
            return Student.get({id : $scope.reasonForLeaving.student.id}).$promise;
        }).then(function(student) {
            $scope.students.push(student);
        });
        $scope.load = function(id) {
            ReasonForLeaving.get({id : id}, function(result) {
                $scope.reasonForLeaving = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:reasonForLeavingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.reasonForLeaving.id != null) {
                ReasonForLeaving.update($scope.reasonForLeaving, onSaveSuccess, onSaveError);
            } else {
                ReasonForLeaving.save($scope.reasonForLeaving, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
