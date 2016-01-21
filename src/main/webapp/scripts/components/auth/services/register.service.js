'use strict';

angular.module('jeducenterApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


