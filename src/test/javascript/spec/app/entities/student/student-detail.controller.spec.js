'use strict';

describe('Controller Tests', function() {

    describe('Student Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStudent, MockUser, MockGroupOfStudent, MockCurator, MockForm, MockLearningResult;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStudent = jasmine.createSpy('MockStudent');
            MockUser = jasmine.createSpy('MockUser');
            MockGroupOfStudent = jasmine.createSpy('MockGroupOfStudent');
            MockCurator = jasmine.createSpy('MockCurator');
            MockForm = jasmine.createSpy('MockForm');
            MockLearningResult = jasmine.createSpy('MockLearningResult');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Student': MockStudent,
                'User': MockUser,
                'GroupOfStudent': MockGroupOfStudent,
                'Curator': MockCurator,
                'Form': MockForm,
                'LearningResult': MockLearningResult
            };
            createController = function() {
                $injector.get('$controller')("StudentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jeducenterApp:studentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
