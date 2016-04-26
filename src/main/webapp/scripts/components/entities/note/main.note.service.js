'use strict';

angular.module('jeducenterApp')
    .factory('MainNote', function ($resource) {
        return $resource('api/main/notes', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
