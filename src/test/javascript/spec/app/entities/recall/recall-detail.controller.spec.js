'use strict';

describe('Controller Tests', function() {

    describe('Recall Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRecall, MockStudent, MockCurator;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRecall = jasmine.createSpy('MockRecall');
            MockStudent = jasmine.createSpy('MockStudent');
            MockCurator = jasmine.createSpy('MockCurator');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Recall': MockRecall,
                'Student': MockStudent,
                'Curator': MockCurator
            };
            createController = function() {
                $injector.get('$controller')("RecallDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jeducenterApp:recallUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
