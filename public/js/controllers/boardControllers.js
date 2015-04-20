"use strict";

var boardControllers = angular.module("controllers.board", []);


boardControllers.controller("boardViewCtrl",['$scope', '$rootScope', 'boardService', 'taskService', function($scope, $rootScope, boardService, taskService) {

    var boardId = 1;
    $scope.refreshBoard = false;
    $scope.board = {}

    boardService.get(boardId).then(function (board){
        $scope.board = board;
        board.$get("columns").then(function (columns){
            $scope.board.columns = columns.items;
            columns.items.forEach(function (column){
                taskService.findByColumn(column.id).then(function (tasks){
                    $scope.refreshBoard = false;
                    column.tasks = tasks.items;
                    var popover = $('.add-popover');
                    if (popover.length)popover.popover();
                });
            });
        });
    });

    $scope.openPointsEditor = function(columnIndex, taskIndex, task){
        var taskInfo = {
                    columnIndex: columnIndex,
                    taskIndex: taskIndex,
                    task: task
                }
        $rootScope.$broadcast("task:setting-task", taskInfo);
        $("#task-log-points-modal").modal("show");
    }

    $scope.openDeleteEditor = function(columnIndex, taskIndex, task){
        var taskInfo = {
            columnIndex: columnIndex,
            taskIndex: taskIndex,
            task: task
        }
        $rootScope.$broadcast("task:setting-task", taskInfo);
        $("#task-delete-modal").modal("show");
    }

    $scope.$on("board:add-task", function(event, task){
        taskService.addToColumn(task, $scope.board.columns[0].id, $scope.board.columns[0].tasks.length).then(function (result){
            $scope.refreshBoard = false;
            $scope.board.columns[0].tasks.push(task);
        });

    });

    $scope.$on("board:remove-task", function(event, taskInfo){
        console.log("column Id: " + taskInfo.columnIndex);
        console.log("task Id: " + taskInfo.taskIndex);
        $scope.board.columns[taskInfo.columnIndex].tasks.splice(taskInfo.taskIndex,1);
    });

    $scope.onDropComplete = function(data, event,task){
        if (!task.users) task.users = [];
        if (task.users.indexOf(data) == -1){
            task.users.push(data);
            taskService.addUser()
        }

    }

    $scope.$watch("board.columns", function (newColumns, oldColumns){
        var tasksChanges = [];

        if ($scope.refreshBoard){
            for (var i=0; i<newColumns.length; i++){
                if (newColumns[i].tasks){
                    if (newColumns[i].tasks.length != oldColumns[i].tasks.length){
						for (var j=0; j<newColumns[i].tasks.length; j++){
							tasksChanges.push({
								boardColumnId: newColumns[i].id,
								taskId: newColumns[i].tasks[j].id,
								position: j
							});
						}
					}else{
                        for (var j=0; j<newColumns[i].tasks.length; j++){
                            if (newColumns[i].tasks[j].id != oldColumns[i].tasks[j].id){
                                tasksChanges.push({
									boardColumnId: newColumns[i].id,
									taskId: newColumns[i].tasks[j].id,
									position: j
								});
                            }
                        }
                    }
                }
            }

            if (tasksChanges.length > 0) taskService.move(tasksChanges);
        }

        $scope.refreshBoard = true;
    }, true);


    var day_data = [
        {"dayOfWeek": "", "expected": 60, "real": 60},
        {"dayOfWeek": "Mon", "expected": 54, "real": 56},
        {"dayOfWeek": "Tue", "expected": 48, "real": 45},
        {"dayOfWeek": "Wed", "expected": 42, "real": 40},
        {"dayOfWeek": "Thu", "expected": 36, "real": 38},
        {"dayOfWeek": "Fri", "expected": 30, "real": 35},
        {"dayOfWeek": "Mon", "expected": 24, "real": 30},
        {"dayOfWeek": "Tue", "expected": 18, "real": 26},
        {"dayOfWeek": "Wed", "expected": 12, "real": 14},
        {"dayOfWeek": "Thu", "expected": 6, "real": 5},
        {"dayOfWeek": "Fri", "expected": 0, "real": 0},
    ];

    Morris.Line({
        element: 'burndown-chart',
        data: day_data,
        xkey: 'dayOfWeek',
        labels: 'dayOfWeek',
        ykeys: ['expected', 'real'],
        gridEnabled: false,
        gridLineColor: 'transparent',
        lineColors: ['red', 'white'],
        lineWidth: [1,2],
        pointSize: [0,4],
        parseTime: false,
        resize:true,
        hideHover: 'auto',
        xLabelAngle: 45,
        gridTextColor: '#fff'
    });
}]);