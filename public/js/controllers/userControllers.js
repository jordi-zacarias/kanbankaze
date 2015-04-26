"use strict";

var userControllers = angular.module("controllers.user", []);


userControllers.controller("teamListCtrl",
    ['$scope', 'userService', function($scope, userService) {

    $scope.users = [];

    $scope.close = function(){
        var asideClass = "aside-in";
        var container = $("#container");

        container.removeClass( asideClass );
    }

    userService.list().then( function (userlist){
        $scope.users = userlist.items;
    });

}]);