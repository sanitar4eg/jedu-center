'use strict';

angular.module('jeducenterApp')
    .controller('TeacherStudentArchiveController', function ($scope, $state, Student, tmhDynamicLocale,
                                                             i18nService, $translate, studentGridService) {
        $scope.loadAll = function (predicate) {
            Student.query(predicate, function (result) {
                $scope.studentsGrid.data = result;
            });
        };

        $scope.loadAll();

        /*Localization*/
        i18nService.setCurrentLang(tmhDynamicLocale.get());

        $scope.studentsGrid = {
            enableGridMenu: true,
            enableColumnResizing: true,
            gridMenuTitleFilter: $translate,
            enableFiltering: true,
            useExternalFiltering: true,
            columnDefs: studentGridService.getColumns(),
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                $scope.gridApi.core.on.filterChanged($scope, function () {
                    var grid = this.grid;
                    var predicate = {};
                    $.extend(predicate, {lastName: grid.columns[0].filters[0].term});
                    $.extend(predicate, {firstName: grid.columns[1].filters[0].term});
                    $.extend(predicate, {middleName: grid.columns[2].filters[0].term});
                    $.extend(predicate, {learningType: grid.columns[3].filters[0].term});
                    $.extend(predicate, {email: grid.columns[4].filters[0].term});
                    $.extend(predicate, {phone: grid.columns[5].filters[0].term});
                    $.extend(predicate, {university: grid.columns[6].filters[0].term});
                    $.extend(predicate, {specialty: grid.columns[7].filters[0].term});
                    $.extend(predicate, {faculty: grid.columns[8].filters[0].term});
                    $.extend(predicate, {course: grid.columns[9].filters[0].term});
                    $.extend(predicate, {isActive: grid.columns[10].filters[0].term});
                    $scope.loadAll(predicate);
                });
            }
        };

        $scope.refresh = function () {
            $scope.loadAll();
        };
    });
