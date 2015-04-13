"use strict";

var boardControllers = angular.module("controllers.board", []);


boardControllers.controller("boardViewCtrl",['$scope', 'boardService', 'taskService', function($scope, boardService, taskService) {

    var boardId = 1;

    $scope.board = {}

    boardService.get(boardId).then(function (board){
        $scope.board = board;
        board.$get("columns").then(function (columns){
            $scope.board.columns = columns.items;
            columns.items.forEach(function (column){
                taskService.findByColumn(column.id).then(function (tasks){
                    column.tasks = tasks.items;
                });
            });
        });
    });


    $scope.$watch("board.columns", function (newColumns, oldColumns){
        var tasksChanges = [];

        if (newColumns){
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
    }, true);
}]);