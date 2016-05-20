'use strict';

angular.module('jeducenterApp')
    .factory('StudentArchiving', function ($resource) {
        return $resource('api/students/archive', {}, {
            'update': {method: 'PUT'},
            'unzip': {method: 'DELETE'}
        });
    });
