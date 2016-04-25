'use strict';

angular.module('jeducenterApp')
    .factory('RecallFile', function ($resource) {
        return $resource('api/recalls/file/:id', {}
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

