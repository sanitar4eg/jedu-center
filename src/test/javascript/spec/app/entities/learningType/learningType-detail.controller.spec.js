'use strict';

describe('Controller Tests', function() {

    describe('LearningType Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLearningType, MockStudent, MockGroupOfStudent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLearningType = jasmine.createSpy('MockLearningType');
            MockStudent = jasmine.createSpy('MockStudent');
            MockGroupOfStudent = jasmine.createSpy('MockGroupOfStudent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LearningType': MockLearningType,
                'Student': MockStudent,
                'GroupOfStudent': MockGroupOfStudent
            };
            createController = function() {
                $injector.get('$controller')("LearningTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jeducenterApp:learningTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
