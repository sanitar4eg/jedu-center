'use strict';

describe('Controller Tests', function() {

    describe('Curator Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCurator, MockUser, MockStudent, MockRecall;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCurator = jasmine.createSpy('MockCurator');
            MockUser = jasmine.createSpy('MockUser');
            MockStudent = jasmine.createSpy('MockStudent');
            MockRecall = jasmine.createSpy('MockRecall');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Curator': MockCurator,
                'User': MockUser,
                'Student': MockStudent,
                'Recall': MockRecall
            };
            createController = function() {
                $injector.get('$controller')("CuratorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jeducenterApp:curatorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
