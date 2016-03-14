'use strict';

angular.module('jeducenterApp')
    .factory('GroupOfStudent', function ($resource, DateUtils) {
        return $resource('api/groupOfStudents/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
