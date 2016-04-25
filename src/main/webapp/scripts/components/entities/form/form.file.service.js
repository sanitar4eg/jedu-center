'use strict';

angular.module('jeducenterApp')
    .factory('FormFile', function ($resource) {
        return $resource('api/apply/form/', {}
            , {
                'uploadFile': {
                    method: 'POST',
                    headers: {'Content-Type': undefined},
                    transformRequest: function (data) {
                        return data;
                    }
                }
            });
    });

