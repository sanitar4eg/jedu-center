'use strict';

angular.module('jeducenterApp')
    .factory('RegisterStudents', function ($resource) {
        return $resource('api/register/students/:students', {}, {
            'update': {
                method: 'POST',
                transformResponse: function (data) {
                    // data = angular.fromJson(data);
                    console.log(data);
                    return data;
                }
            }
        });
    });


