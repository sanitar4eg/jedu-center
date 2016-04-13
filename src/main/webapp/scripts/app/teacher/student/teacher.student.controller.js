'use strict';

angular.module('jeducenterApp')
    .controller('TeacherStudentController', function ($scope, $state, Student) {

        $scope.students = [];
        $scope.myData = [];
        $scope.loadAll = function () {
            Student.query(function (result) {
                $scope.students = result;
                $scope.myData = result;
                $scope.studentsGrid.data = result;
            });
        };

        $scope.loadAll();

        $scope.studentsGrid = {
            // enableSorting: false,
            enableGridMenu: true,
            enableColumnResizing: true,
            columnDefs: [
                {name: 'Имя', field: 'firstName', width: '30%'},
                {name: 'Фамилия', field: 'lastName'},
                {name: 'Тип обучения', field: 'type'},
                {name: 'Email', field: 'email'},
                {name: 'Телефон', field: 'phone'},
                {name: 'Университет', field: 'university'},
                {name: 'Специальность', field: 'specialty'},
                {name: 'Курс', field: 'course'},
                {name: 'Активен', field: 'isActive'}
            ]

            // columnDefs: [{name: 'Имя', field: 'firstName'}],
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
