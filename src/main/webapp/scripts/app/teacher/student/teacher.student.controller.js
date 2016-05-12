'use strict';

angular.module('jeducenterApp')
    .controller('TeacherStudentController', function ($scope, $state, Student, tmhDynamicLocale,
                                                      i18nService, $translate, $log) {

        $scope.students = [];
        $scope.options = [
            {text: "Все", predicate: {}},
            {text: "DEV", predicate: {type: "DEV"}},
            {text: "QA", predicate: {type: "QA"}}
        ];
        $scope.studentsPredicate = {isActive: true};

        $scope.loadAll = function (predicate) {
            var resultPredicate = $.extend($scope.studentsPredicate, predicate);
            Student.query(resultPredicate, function (result) {
                $scope.students = result;
                $scope.studentsGrid.data = result;
            });
        };

        $scope.updateSelect = function (option) {
            $scope.loadAll($scope.option.predicate);
        };


        $scope.loadAll();

        /*Localization*/
        i18nService.setCurrentLang(tmhDynamicLocale.get());

        $scope.studentsGrid = {
            enableGridMenu: true,
            enableColumnResizing: true,
            gridMenuTitleFilter: $translate,
            columnDefs: [
                {
                    displayName: 'jeducenterApp.student.lastName', field: 'lastName', width: '12%',
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.firstName', field: 'firstName', width: '10%',
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.middleName', field: 'middleName', width: '12%',
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.type', field: 'type', width: '4%',
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.email', field: 'email', width: '14%',
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.phone', field: 'phone', width: '11%',
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.university', field: 'university', width: '10%',
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.specialty', field: 'specialty', width: '12%', visible: false,
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.faculty', field: 'faculty', width: '12%', visible: false,
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.course', field: 'course', width: '4%', visible: false,
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.groupOfStudent',
                    name: 'groupOfStudent', width: '8%', visible: false, headerCellFilter: "translate",
                    enableCellEdit: false,
                    cellTemplate: 'scripts/app/teacher/student/ui-grid/student.group.cell.html'
                },
                {
                    displayName: 'jeducenterApp.student.curator', name: 'curator', width: '8%', visible: false,
                    headerCellFilter: "translate", enableCellEdit: false,
                    cellTemplate: 'scripts/app/teacher/student/ui-grid/student.curator.cell.html'
                },
                {
                    name: ' ', width: '10%', enableSorting: false, enableCellEdit: false,
                    cellTemplate: 'scripts/app/teacher/student/ui-grid/student.buttons.html'
                }
            ]
        };

        $scope.studentsGrid.onRegisterApi = function (gridApi) {
            $scope.gridApi = gridApi;
            gridApi.edit.on.afterCellEdit($scope, function (rowEntity, colDef, newValue, oldValue) {
                Student.update(rowEntity, onSaveSuccess);
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:studentUpdate', result);
        };


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.student = {
                firstName: null,
                lastName: null,
                middleName: null,
                type: null,
                email: null,
                phone: null,
                university: null,
                specialty: null,
                course: null,
                isActive: false,
                id: null
            };
        };
    });
