'use strict';

angular.module('jeducenterApp')
    .factory('studentGridService', function (LearningType, uiGridConstants) {
        var learningTypes = [];
        LearningType.query(function (result) {
            result.forEach(function (item) {
                learningTypes.push({value: item.id, label: item.name})
            });
        });
        var studentsColumnDefinition = [
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
                displayName: 'jeducenterApp.student.type', field: 'learningType.name', width: '6%',
                headerCellFilter: "translate",
                filter: {
                    type: uiGridConstants.filter.SELECT,
                    selectOptions: learningTypes
                }
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
                headerCellFilter: "translate",
                filter: {
                    type: uiGridConstants.filter.SELECT,
                    selectOptions: [{value: 'СГУ', label: 'СГУ'}, {value: 'СГТУ', label: 'СГТУ'}]
                }
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
                displayName: 'jeducenterApp.student.isActive', field: 'isActive', width: '8%', type: 'boolean',
                visible: false, headerCellFilter: "translate",
                filter: {
                    type: uiGridConstants.filter.SELECT,
                    selectOptions: [{value: 'true', label: 'true'}, {value: 'false', label: 'false'}]
                }
            },
            {
                displayName: 'jeducenterApp.student.groupOfStudent', name: 'groupOfStudent', width: '8%',
                visible: false, enableFiltering: false, headerCellFilter: "translate",
                cellTemplate: 'scripts/app/teacher/student/ui-grid/student.group.cell.html'
            },
            {
                displayName: 'jeducenterApp.student.curator', name: 'curator', width: '8%',
                visible: false, enableFiltering: false, headerCellFilter: "translate",
                cellTemplate: 'scripts/app/teacher/student/ui-grid/student.curator.cell.html'
            },
            {
                name: ' ', width: '14%', enableSorting: false, enableFiltering: false,
                cellTemplate: 'scripts/app/teacher/student/archive/ui-grid/student.archive.buttons.html'
            }
        ];

        var getColumns = function () {
            return studentsColumnDefinition;
        };

        return {
            getColumns: getColumns
        };
    });
