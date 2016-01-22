'use strict';

angular.module('jeducenterApp')
    .factory('StudentHistory', function ($resource, DateUtils) {
        return $resource('api/history/students/:dateTime', {}, {
            'query':  {
                method: 'GET', isArray: true,
                transformResponse: function(data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        })
    });
