'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cur-tab.recall.new', {
                parent: 'cur-tab',
                url: '/cur-tab/recall/new',
                data: {
                    authorities: ['ROLE_CURATOR']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/recall/recall-dialog.html',
                        controller: 'RecallDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    name: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('recall', null, { reload: true });
                    }, function() {
                        $state.go('recall');
                    })
                }]
            })
    });
