'use strict';

angular.module('jeducenterApp')
    .factory('TimeTable', function ($resource, DateUtils) {
        return $resource('api/timeTables/:id', {}, {
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
