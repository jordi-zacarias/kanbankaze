"use strict";

var taskServices = angular.module('services.tasks', []);

taskServices.factory('taskService', ['$http', 'halClient', 'appSettings', function($http, halClient, appSettings){

    var taskByBoardUrl = appSettings.apiBaseUri + "/task/tasks-by-board/";
    var taskByColumnUrl = appSettings.apiBaseUri + "/task/columns-by-column/";
    var moveUrl = appSettings.apiBaseUri + "/column/update-list-columns";
    var saveUrl = appSettings.apiBaseUri + "/task";

    return {
        findByBoard: function (boardId){
            return halClient.$get(taskByBoardUrl + boardId);
        },

        findByColumn: function (columnId){
            return halClient.$get(taskByColumnUrl + columnId);
        },

        move: function(columnTasks){
            return halClient.$post(moveUrl, {}, columnTasks);
        },

        save: function(task){
            return halClient.$post(saveUrl, {}, task);
        }
    }
}]);
