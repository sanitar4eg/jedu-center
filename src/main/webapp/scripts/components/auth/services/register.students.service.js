'use strict';

angular.module('jeducenterApp')
    .factory('RegisterStudents', function ($resource) {
        return $resource('api/register/students/:students', {}, {
            'update': {
                method: 'POST',
                isArray: true,
                transformRequest: function (input) {
                    console.log(JSON.stringify(input));
                    return input;
                },
                transformResponse: function (data) {
                    // data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });


