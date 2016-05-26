'use strict';

angular.module('jeducenterApp')
    .controller('TeacherStudentController', function ($scope, $state, Student, tmhDynamicLocale, $window,
                                                      i18nService, $translate, $log, LearningType) {

        $scope.students = [];
        $scope.types = LearningType.query();
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
                    headerCellFilter: "translate",
                    validators: {required: true}, cellTemplate: 'ui-grid/cellTooltipValidator'
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
                    displayName: 'jeducenterApp.student.learningType', width: '4%', name: 'learningType',
                    headerCellFilter: "translate",
                    cellTemplate: 'scripts/app/teacher/student/ui-grid/student.type.cell.html',
                    editDropdownOptionsArray: $scope.types, editDropdownValueLabel: 'name',
                    editableCellTemplate: 'scripts/app/teacher/student/ui-grid/student.type.select.html'
                },
                {
                    // TODO: Fixed this

                    displayName: 'jeducenterApp.student.email', field: 'email', width: '14%',
                    headerCellFilter: "translate", type: 'email',
                    validators: {required: true}, cellTemplate: 'ui-grid/cellTitleValidator'
                },
                {
                    displayName: 'jeducenterApp.student.phone', field: 'phone', width: '11%',
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.university', field: 'university', width: '10%',
                    headerCellFilter: "translate", editDropdownOptionsArray: ['СГУ', 'СГТУ'],
                    editableCellTemplate: 'scripts/app/teacher/student/ui-grid/student.university.select.html'
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
                    displayName: 'jeducenterApp.student.gotJob', field: 'gotJob', width: '6%', visible: false,
                    headerCellFilter: "translate", type: 'boolean'
                },
                {
                    displayName: 'jeducenterApp.student.comment', field: 'comment', width: '10%', visible: false,
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
                Student.update(rowEntity, onSaveSuccess, onSaveError);
            });
            gridApi.validate.on.validationFailed($scope,function(rowEntity, colDef, newValue, oldValue){
                $window.alert("Не правильно заполнено поле " + $translate.instant(colDef.displayName)
                    + "\nРезультат не сохранен!");
                $scope.refresh();
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:studentUpdate', result);
        };

        var onSaveError = function (result) {
            $scope.refresh();
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
