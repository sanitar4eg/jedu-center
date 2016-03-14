'use strict';

describe('Controller Tests', function() {

    describe('TimeTable Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTimeTable, MockGroupOfStudent, MockLesson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTimeTable = jasmine.createSpy('MockTimeTable');
            MockGroupOfStudent = jasmine.createSpy('MockGroupOfStudent');
            MockLesson = jasmine.createSpy('MockLesson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TimeTable': MockTimeTable,
                'GroupOfStudent': MockGroupOfStudent,
                'Lesson': MockLesson
            };
            createController = function() {
                $injector.get('$controller')("TimeTableDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jeducenterApp:timeTableUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
