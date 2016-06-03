'use strict';

angular.module('jeducenterApp')
    .factory('CurTabStudent', function ($resource) {
        return $resource('api/cur-tab/students/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
