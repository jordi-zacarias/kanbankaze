"use strict";

var taskServices = angular.module('services.tasks', []);

taskServices.factory('taskService', ['$http', 'halClient', 'appSettings', function($http, halClient, appSettings){

    var taskByBoardUrl = appSettings.apiBaseUri + "/task/tasks-by-board/";
    var taskByColumnUrl = appSettings.apiBaseUri + "/task/query-by-column/";
    var moveUrl = appSettings.apiBaseUri + "/column/update-list-columns";
    var addToColumnUrl = appSettings.apiBaseUri + "/column/insert-task";
    var saveUrl = appSettings.apiBaseUri + "/task";

    var addUserUrl = appSettings.apiBaseUri + "/taskUser";

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

        addToColumn: function(task, columnId, position){
            var data = {
                boardColumnId: columnId,
                taskId: task.id,
                position: position
            }
            return halClient.$post(addToColumnUrl, {}, data);
        },

        save: function(task){
            return halClient.$post(saveUrl, {}, task);
        },

        delete: function(task){
            return halClient.$del(saveUrl + "/" + task.id);
        },

        addUser: function(task, userGuid){
            var data = {
                taskId: task.id,
                userGuid: userGuid
            };
            return halClient.$post(addUserUrl, {}, data);
        }
    }
}]);
