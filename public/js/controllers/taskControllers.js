"use strict";

var loginControllers = angular.module("controllers.task", []);


loginControllers.controller("taskEditCtrl",
    ['$scope', '$rootScope', 'taskService', function($scope, $rootScope, taskService) {

    function resetValidation(){
        $scope.validation = {
            title: "",
            description: "",
            estimation: ""
        }
    }

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
        resetValidation();
    }

    function validateForm(task){

        $scope.validation.title = (task.title === "") ? "This field cannot be empty" : "";
        $scope.validation.description = (task.description === "") ? "This field cannot be empty" : "";
        try{
            var estimation = parseInt(task.estimation);
            $scope.validation.estimation = (estimation <= 0) ? "You must specify a number great than 0" : "";
        }catch(e){
            $scope.validation.estimation = "This field must be a number great than 0";
        }

        return (
            $scope.validation.title === "" &&
            $scope.validation.description === "" &&
            $scope.validation.estimation === ""
        );
    }

    $scope.save = function(){
        if (validateForm($scope.task)){
            $scope.task.estimation = parseInt($scope.task.estimation);
            taskService.save($scope.task).then(
                function(savedTask){
                    resetTaskInfo();
                    $rootScope.$broadcast("board:add-task", savedTask);

                    $('#task-edit-modal').modal('hide');
                }
            );
        }
    }

    resetTaskInfo();

}]);

loginControllers.controller("taskPointsCtrl", ['$scope', 'taskService', function($scope, $rootScope, taskService) {

    $scope.task = null;
    $scope.points = {
        date: "",
        assigned: ""
    }

    $scope.$on("task:setting-task", function(event, taskInfo){
        $scope.taskInfo = taskInfo;
    });

    $('#pointsDate').datepicker({format:'dd/mm/yyyy', autoclose:true});
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