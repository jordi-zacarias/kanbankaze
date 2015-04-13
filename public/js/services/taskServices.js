"use strict";

var taskServices = angular.module('services.tasks', []);

taskServices.factory('taskService', ['$http', 'halClient', 'appSettings', function($http, halClient, appSettings){

    var taskByBoardUrl = appSettings.apiBaseUri + "/task/tasks-by-board/";
    var taskByColumnUrl = appSettings.apiBaseUri + "/task/columns-by-column/"

    return {
        findByBoard: function (boardId){
            return halClient.$get(taskByBoardUrl + boardId);
        },
        findByColumn: function (columnId){
            return halClient.$get(taskByColumnUrl + columnId);
        }
    }
}]);
