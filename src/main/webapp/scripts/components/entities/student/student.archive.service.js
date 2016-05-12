'use strict';

angular.module('jeducenterApp')
    .factory('StudentArchive', function ($resource) {
        return $resource('api/students/archive/:id', {}, {
            'update': { method:'PUT' }
        });
    });
