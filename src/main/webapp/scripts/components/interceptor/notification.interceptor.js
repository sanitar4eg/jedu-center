 'use strict';

angular.module('jeducenterApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-jeducenterApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-jeducenterApp-params')});
                }
                return response;
            }
        };
    });
