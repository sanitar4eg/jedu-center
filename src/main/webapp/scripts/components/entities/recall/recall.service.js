'use strict';

angular.module('jeducenterApp')
    .factory('Recall', function ($resource, DateUtils) {
        return $resource('api/recalls/:id', {}, {
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
