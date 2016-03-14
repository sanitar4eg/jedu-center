'use strict';

describe('Controller Tests', function() {

    describe('Lesson Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLesson, MockTimeTable, MockEvaluation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLesson = jasmine.createSpy('MockLesson');
            MockTimeTable = jasmine.createSpy('MockTimeTable');
            MockEvaluation = jasmine.createSpy('MockEvaluation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Lesson': MockLesson,
                'TimeTable': MockTimeTable,
                'Evaluation': MockEvaluation
            };
            createController = function() {
                $injector.get('$controller')("LessonDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jeducenterApp:lessonUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
