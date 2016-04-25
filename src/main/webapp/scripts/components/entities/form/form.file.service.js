'use strict';

angular.module('jeducenterApp')
    .factory('ApplyFile', function ($resource) {
        return $resource('api/apply/form/', {}
            , {
                'uploadApply': {
                    method: 'POST',
                    headers: {'Content-Type': undefined},
                    transformRequest: function (data) {
                        return data;
                    }
                }
                
            });
    })
    .factory('FormFile', function ($resource) {
        return $resource('api/forms/file/:id', {}
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

