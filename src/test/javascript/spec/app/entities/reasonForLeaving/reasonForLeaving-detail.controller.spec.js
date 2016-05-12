'use strict';

describe('Controller Tests', function() {

    describe('ReasonForLeaving Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockReasonForLeaving, MockStudent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockReasonForLeaving = jasmine.createSpy('MockReasonForLeaving');
            MockStudent = jasmine.createSpy('MockStudent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ReasonForLeaving': MockReasonForLeaving,
                'Student': MockStudent
            };
            createController = function() {
                $injector.get('$controller')("ReasonForLeavingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jeducenterApp:reasonForLeavingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
