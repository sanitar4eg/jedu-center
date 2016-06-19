'use strict';

angular.module('jeducenterApp')
    .factory('CurrentEntityId', function ($resource) {
        return $resource('api/current/', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    return angular.fromJson(data);
                }
            }
        });
    });

