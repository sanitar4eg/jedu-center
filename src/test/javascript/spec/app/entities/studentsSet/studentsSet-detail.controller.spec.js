'use strict';

describe('Controller Tests', function() {

    describe('StudentsSet Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStudentsSet, MockStudent, MockGroupOfStudent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStudentsSet = jasmine.createSpy('MockStudentsSet');
            MockStudent = jasmine.createSpy('MockStudent');
            MockGroupOfStudent = jasmine.createSpy('MockGroupOfStudent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'StudentsSet': MockStudentsSet,
                'Student': MockStudent,
                'GroupOfStudent': MockGroupOfStudent
            };
            createController = function() {
                $injector.get('$controller')("StudentsSetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jeducenterApp:studentsSetUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
