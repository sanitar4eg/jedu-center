'use strict';

angular.module('jeducenterApp')
    .factory('StudentIntegration', function ($resource) {
        return $resource('api/import/students/:id', {}
            , {
                'uploadFile': {
                    method: 'POST',
                    headers: {'Content-Type': undefined},
                    transformRequest: function (data) {
                        return data;
                    },
                    isArray: true,
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                }
            });
    });

