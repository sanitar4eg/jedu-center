'use strict';

angular.module('jeducenterApp')
    .controller('TeacherStudentArchiveController', function ($scope, $state, Student, tmhDynamicLocale,
                                                      i18nService, $translate) {

        $scope.students = [];
        $scope.options = [
            {text:"Все", predicate: {}},
            {text:"DEV", predicate: {type: "DEV"}},
            {text:"QA", predicate: {type: "QA"}}
        ];

        $scope.loadAll = function (predicate) {
            Student.query(predicate, function (result) {
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
            enableFiltering: true,
            useExternalFiltering: true,
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
                    displayName: 'jeducenterApp.student.course', field: 'course', width: '4%', visible: false,
                    headerCellFilter: "translate"
                },
                {
                    displayName: 'jeducenterApp.student.isActive', field: 'isActive', width: '8%', type: 'boolean',
                    visible: false,
                    headerCellTemplate: '<span translate="jeducenterApp.student.isActive"/>'
                },
                {
                    displayName: 'jeducenterApp.student.groupOfStudent', name: 'groupOfStudent', width: '8%', visible: false,
                    headerCellFilter: "translate",
                    cellTemplate: 'scripts/app/teacher/student/ui-grid/student.group.cell.html'
                },
                {
                    displayName: 'jeducenterApp.student.curator', name: 'curator', width: '8%', visible: false,
                    headerCellFilter: "translate",
                    cellTemplate: 'scripts/app/teacher/student/ui-grid/student.curator.cell.html'
                },
                {
                    name: ' ', width: '14%', enableSorting: false, enableFiltering: false,
                    cellTemplate: 'scripts/app/teacher/student/archive/ui-grid/student.archive.buttons.html'
                }
            ]

        };

        $scope.refresh = function () {
            $scope.loadAll();
        };
    });
