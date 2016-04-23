'use strict';

angular.module('jeducenterApp').controller('CuratorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Curator', 'User', 'Student', 'Recall',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Curator, User, Student, Recall) {

        $scope.curator = entity;
        $scope.users = User.query();
        $scope.students = Student.query();
        $scope.recalls = Recall.query();
        $scope.load = function(id) {
            Curator.get({id : id}, function(result) {
                $scope.curator = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:curatorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.curator.id != null) {
                Curator.update($scope.curator, onSaveSuccess, onSaveError);
            } else {
                Curator.save($scope.curator, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
