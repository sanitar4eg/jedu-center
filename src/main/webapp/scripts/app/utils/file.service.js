'use strict';

angular.module('jeducenterApp')
    .service('fileService', function () {
        var file;
        var fileService = {};

        fileService.getFile = function () {
            return file;
        };

        fileService.setFile = function (newFile) {
            file = newFile;
        };

        return fileService;
    });
