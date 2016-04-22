'use strict';

angular.module('jeducenterApp')
    .controller('TeacherStudentController', function ($scope, $state, Student, tmhDynamicLocale,
                                                      i18nService) {

        $scope.students = [];
        $scope.loadAll = function () {
            Student.query(function (result) {
                $scope.students = result;
                $scope.studentsGrid.data = result;
            });
        };

        $scope.loadAll();

        /*Localization*/
        i18nService.setCurrentLang(tmhDynamicLocale.get());

        $scope.studentsGrid = {
            enableGridMenu: true,
            enableColumnResizing: true,
            columnDefs: [
                {
                    field: 'firstName', width: '10%',
                    headerCellTemplate: '<span translate="jeducenterApp.student.firstName"/>'
                },
                {
                    field: 'middleName', width: '12%',
                    headerCellTemplate: '<span translate="jeducenterApp.student.middleName"/>'
                },
                {
                    field: 'lastName', width: '12%',
                    headerCellTemplate: '<span translate="jeducenterApp.student.lastName"/>'
                },
                {
                    field: 'type', width: '8%',
                    headerCellTemplate: '<span translate="jeducenterApp.student.type"/>'
                },
                {
                    field: 'email', width: '14%',
                    headerCellTemplate: '<span translate="jeducenterApp.student.email"/>'
                },
                {
                    field: 'phone', width: '11%',
                    headerCellTemplate: '<span translate="jeducenterApp.student.phone"/>'
                },
                {
                    field: 'university', width: '10%',
                    headerCellTemplate: '<span translate="jeducenterApp.student.university"/>'
                },
                {
                    field: 'specialty', width: '12%', visible: false,
                    headerCellTemplate: '<span translate="jeducenterApp.student.specialty"/>'
                },
                {
                    field: 'course', width: '4%', visible: false,
                    headerCellTemplate: '<span translate="jeducenterApp.student.course"/>'
                },
                {
                    field: 'isActive', width: '8%', type: 'boolean',
                    headerCellTemplate: '<span translate="jeducenterApp.student.isActive"/>'
                },
                {
                    name: ' ', width: '8%'/*'8%'*/,
                    cellTemplate:
                    '<div class="btn-group flex-btn-group-container">' +
                    '   <button type="submit"' +
                    '   ui-sref="teacher.student.detail({id:{{row.entity.id}}})"' +
                    '   class="btn btn-info btn-sm">' +
                    '       <span class="glyphicon glyphicon-eye-open">' +
                    '   </button>' +
                    '   <button type="submit"' +
                    '   ui-sref="teacher.student.edit({id:{{row.entity.id}}})"' +
                    '   class="btn btn-primary btn-sm">' +
                    '       <span class="glyphicon glyphicon-pencil">' +
                    '   </button>' +
                    '   <button type="submit"' +
                    '   ui-sref="teacher.student.delete({id:{{row.entity.id}}})"' +
                    '   class="btn btn-danger btn-sm">' +
                    '       <span class="glyphicon glyphicon-remove-circle">' +
                    '   </button>' +
                    '</div>'
                }
            ]

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
