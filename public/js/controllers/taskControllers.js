"use strict";

var loginControllers = angular.module("controllers.task", []);


loginControllers.controller("taskEditCtrl",
    ['$scope', '$rootScope', 'taskService', function($scope, $rootScope, taskService) {

    $scope.task = {
        id: 0,
        title: "",
        description: "",
        estimation: 0,
        acceptanceCriteria: "",
        blocked: false,
        blockedReason: ""
    };

    $scope.save = function(){
        $scope.task.estimation = parseInt($scope.task.estimation);
        taskService.save($scope.task).then(
            function(savedTask){
                $rootScope.$broadcast("board:add-task", savedTask);
            }
        );
    }

}]);
