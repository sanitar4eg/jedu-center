'use strict';

angular.module('jeducenterApp')
    .factory('CurrentEntity', function ($resource) {
        return $resource('api/current/', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    return angular.fromJson(data);
                }
            }
        });
    });

