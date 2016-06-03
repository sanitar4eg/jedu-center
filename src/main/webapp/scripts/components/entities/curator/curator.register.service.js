'use strict';

angular.module('jeducenterApp')
    .factory('CuratorRegister', function ($resource, DateUtils) {
        return $resource('api/register/curators/:id', {}, {
            'register': { method:'PUT' }
        });
    });
