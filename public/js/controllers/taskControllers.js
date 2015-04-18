"use strict";

var loginControllers = angular.module("controllers.task", []);


loginControllers.controller("taskEditCtrl",
    ['$scope', '$rootScope', 'taskService', function($scope, $rootScope, taskService) {

    function resetTaskInfo(){
        $scope.task = {
            id: 0,
            title: "",
            description: "",
            estimation: 0,
            acceptanceCriteria: "",
            blocked: false,
            blockedReason: ""
        };
    }

    $scope.save = function(){
        $scope.task.estimation = parseInt($scope.task.estimation);
        taskService.save($scope.task).then(
            function(savedTask){
                resetTaskInfo();
                $rootScope.$broadcast("board:add-task", savedTask);

                $('#task-edit-modal').modal('hide');
            }
        );
    }

    resetTaskInfo();

}]);

loginControllers.controller("taskPointsCtrl", ['$scope', 'taskService', function($scope, $rootScope, taskService) {

    $scope.task = null;

    $scope.$on("task:setting-task", function(event, task){
        $scope.task = task;
    });
}]);

loginControllers.controller("taskDeleteCtrl", ['$scope', '$rootScope', 'taskService', function($scope, $rootScope, taskService) {

    $scope.taskInfo = null;

    $scope.$on("task:setting-task", function(event, taskInfo){
        $scope.taskInfo = taskInfo;
    });

    $scope.delete = function(){
        taskService.delete($scope.taskInfo.task).then(function(){
            $rootScope.$broadcast("board:remove-task", $scope.taskInfo);
            $("#task-delete-modal").modal("hide");
        });
    }
}]);