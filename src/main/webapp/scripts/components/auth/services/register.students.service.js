'use strict';

angular.module('jeducenterApp')
    .factory('RegisterStudents', function ($resource) {
        return $resource('api/register/students/:students', {}, {
            'update': {
                method: 'POST',
                isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });


