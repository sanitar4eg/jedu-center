'use strict';

angular.module('jeducenterApp')
    .factory('StudentIntegration', function ($resource) {
        return $resource('api/import/students/'
            , {
            'query': {method: 'GET', isFile: true}
            /*'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'}*/
        });
    });

