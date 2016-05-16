'use strict';

angular.module('jeducenterApp')
    .factory('StudentsSet', function ($resource, DateUtils) {
        return $resource('api/studentsSets/:id', {}, {
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
