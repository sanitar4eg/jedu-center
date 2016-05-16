'use strict';

describe('Controller Tests', function() {

    describe('GroupOfStudent Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGroupOfStudent, MockStudent, MockTimeTable, MockStudentsSet, MockLearningType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGroupOfStudent = jasmine.createSpy('MockGroupOfStudent');
            MockStudent = jasmine.createSpy('MockStudent');
            MockTimeTable = jasmine.createSpy('MockTimeTable');
            MockStudentsSet = jasmine.createSpy('MockStudentsSet');
            MockLearningType = jasmine.createSpy('MockLearningType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'GroupOfStudent': MockGroupOfStudent,
                'Student': MockStudent,
                'TimeTable': MockTimeTable,
                'StudentsSet': MockStudentsSet,
                'LearningType': MockLearningType
            };
            createController = function() {
                $injector.get('$controller')("GroupOfStudentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jeducenterApp:groupOfStudentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
